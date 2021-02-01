import logging
import json
import smtplib

# for creating the zip file
import os, shutil
import requests

from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from airflow.operators.python_operator import BranchPythonOperator
from airflow.utils.task_group import TaskGroup
from airflow.utils.dates import days_ago
from pprint import pprint

# default arguments
default_args = {
    'owner': 'RishiS',
    'depends_on_past': False,
    'start_date': datetime(2019, 2, 15),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
    'provide_context': True,    
}

# [START instantiate_dag]
with DAG(
    dag_id='VMCreationPOC',
    default_args=default_args,
    start_date=datetime(2015, 12,1),
    schedule_interval=None,
    tags=['VMCreationPOC']
) as dag:
    dag.doc_md = __doc__
        
    ## This method will fetch terraform files and create a zip out of it.
    def fetch_terraform_generate_zip(**kwargs):
        ti = kwargs['ti']
        logging.info("Entering into fetch_terraform_generate_zip step...")
        logging.info("fetch and generate zip of terraform files...")
        source = '/home/rishis/development/git-code/airflow/airflow-terraform'
        destination = '/home/rishis/development/git-code/airflow/airflow-terraform.zip'
        base = os.path.basename(destination)
        name = base.split('.')[0]
        format = base.split('.')[1]
        archive_from = os.path.dirname(source)
        archive_to = os.path.basename(source.strip(os.sep))
        print(source, destination, archive_from, archive_to)
        shutil.make_archive(name, format, archive_from, archive_to)
        shutil.move('%s.%s'%(name,format), destination)
        ## return path of the zip file
        logging.info("Zip file has been generated successfully...")
        tf_zip_path = '/home/rishis/development/git-code/airflow/airflow-terraform.zip'
        ti.xcom_push('tf_zip_path', tf_zip_path)
    

    # This method will make initial entries to database
    def update_db(**kwargs):
        ti = kwargs['ti']
        tf_zip_path = ti.xcom_pull(task_ids='fetch_terraform_generate_zip', key='tf_zip_path')
        print("Received value of tf_zip_path :: " + tf_zip_path)
        logging.info("-----====== DB Entry has been made ======--------")

    # This method will unzip terraform file
    def extract_tf_package(**kwargs):
        ti = kwargs['ti']
        tf_zip_path = ti.xcom_pull(task_ids='fetch_terraform_generate_zip', key='tf_zip_path')
        print("Received value of tf_zip_path :: " + tf_zip_path)
        tf_unzip_path = "/home/rishis/development/git-code/airflow/extract_dir"
        shutil.unpack_archive(tf_zip_path, tf_unzip_path, "zip")
        logging.info("zip fle unpacked successfully.")
        ti.xcom_push('tf_unzip_path', tf_unzip_path)

    
    # Send Email to Notify Manager to Seek approval
    def notify(**kwargs):
        ti = kwargs['ti']
        tf_zip_path = ti.xcom_pull(task_ids='fetch_terraform_generate_zip', key='tf_zip_path')
        print("User is about to provision resources using terraform resources :: " + tf_zip_path)
        content = """
            <html>  
                <body>
                    <p>User is about to Provision resource in AWS. You need to Approve or Reject</p>
                    <a href='http://localhost:5000/approval?approval=true'>Approve</a>&nbsp;
                    <a href='http://localhost:5000/approval?approval=false'>Reject</a>
                </body>
            </html>
        """
        try:
            smtpObj = smtplib.SMTP('localhost', 25)
            smtpObj.sendmail("airfowpoc.org", "rishi@gmail.com", content)         
            print("Successfully sent email")
        except smtplib.SMTPException:
            print("Error: unable to send email")
    
    
    # Read Approval Data from Database and take decision accordingly
    def check_approval(**kwargs):
        # Make Rest Call to fetch what we have in Database
        print("------ Making REST Call to fetch Approval Resonse------")
        response = requests.get("http://localhost:5000/checkApproval")
        print(response.status_code)
        print(response.text)
        # For successful API call, response code will be 200 (OK)
        if(response.ok):
            print("------Successfully get Approval Resonse ::: " + response.text)
            data = response.text
            if data == "True":
                approved = True
            elif data == "False":
                approved = False
            print(approved)
            if approved:  
                return "approved"
            else:
                return "rejected"
        else:
            print("------Failed to get Approval Response ::: ")
       
    
    ## When Manager rejected the approval request
    def rejected(**kwargs):
        print("Your Manager have rejected your request")
    
    ## When Manager rejected the approval request
    def approved(**kwargs):
        print("Your Manager have approved your request")

        
    # TASK GROUP
    with TaskGroup("terraform_execution_task_group", tooltip="Terraform commands in Task Group") as terraform_execution_task_group:
        terraformInitPlanApply = BashOperator(
            task_id="terraformInitPlanApply", 
            bash_command='\
                cd /home/rishis/development/git-code/airflow/extract_dir/airflow-terraform \
                && echo "---Running Terraform Init---" \
                && terraform init \
               && echo "--Running Terraform Plan---" \
                && terraform plan \
                && echo "---Running Terraform Apply and wait for 5 secs----" \
                && terraform apply -auto-approve \
                && sleep 5 \
                && echo "---Running Terraform Show to generate TF State File as JSON----" \
                && terraform show -json > output.json \
                && sleep 5')        
    ## END TASK GROUP

    # This method will load and print the TfStateJson
    def push_tfstate_file_db(**kwargs):
        #ti = kwargs['ti']
        logging.info("-----====== Load and Push TF Json File ======--------")
        tfStateJson = open('/home/rishis/development/git-code/airflow/extract_dir/airflow-terraform/output.json',)
        tfStateData = json.load(tfStateJson)
        print("JSON string = ", tfStateData)         
        return tfStateData               

    
    # This method will make entry to database along with terraform state file entries
    def update_db_inventory(**kwargs):
        ti = kwargs['ti']
        tfStateJsonData = ti.xcom_pull(task_ids='push_tfstate_file_db', key='tfStateData')
        print("Received value of tfStateJsonData :: " , tfStateJsonData)
        logging.info("-----====== DB Entry has been made ======--------")
    

    ######### TASKS ################
    fetch_terraform_generate_zip = PythonOperator(
        task_id='fetch_terraform_generate_zip',
        provide_context=True, 
        python_callable=fetch_terraform_generate_zip,)

    update_db = PythonOperator(
        task_id='update_db',
        provide_context=True, 
        python_callable=update_db,)

    extract_tf_package = PythonOperator(
        task_id='extract_tf_package', 
        provide_context=True,
        python_callable=extract_tf_package,)

    notify = PythonOperator(
        task_id='notify',
        python_callable=notify,
        retry_delay=timedelta(seconds=30),
        retries=5,
        provide_context=True,)    
    
    check_approval = BranchPythonOperator(
        task_id='check_approval',
        python_callable=check_approval,
        provide_context=True,
        retry_delay=timedelta(seconds=30),
        retries=5,)

    approved = PythonOperator(
        task_id='approved',
        python_callable=approved,
        provide_context=True,
        dag=dag,)

    rejected = PythonOperator(
        task_id='rejected',
        python_callable=rejected,
        provide_context=True,
        dag=dag,)
    
    push_tfstate_file_db = PythonOperator(
        task_id='push_tfstate_file_db', 
        provide_context=True,
        python_callable=push_tfstate_file_db,)
    
    terraform_destroy = BashOperator(
        task_id='terraform_destroy',
        bash_command='\
            cd /home/rishis/development/git-code/airflow/extract_dir/airflow-terraform \
            && echo "---- Running Terraform Destroy ---" \
            && terraform destroy -auto-approve',)        

    update_db_inventory = PythonOperator(
        task_id='update_db_inventory', 
        provide_context=True,
        python_callable=update_db_inventory,)

    # Execution
    fetch_terraform_generate_zip >> update_db
    fetch_terraform_generate_zip >> extract_tf_package >> notify >> check_approval >> [approved, rejected]
    approved >> terraform_execution_task_group >> [push_tfstate_file_db, terraform_destroy]
    push_tfstate_file_db >> [update_db_inventory]
