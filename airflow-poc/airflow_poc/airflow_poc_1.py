from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.operators.python_operator import PythonOperator
from airflow.utils.dates import days_ago

default_args = {
    'owner': 'RishiS',
    'depends_on_past': False,
    'start_date': datetime(2019, 2, 15),
    'email_on_failure': False,
    'email_on_retry': False,
    }

dag = DAG(
    dag_id='airflow_poc_1',
    default_args=default_args,
    start_date=datetime(2015, 12,1),
    schedule_interval=None,
    catchup=False
)

def print_hello():
    return 'Hello world!'

dummy_operator = DummyOperator(
    task_id='dummy_task', 
    retries=0, 
    dag=dag)

hello_operator = PythonOperator(
    task_id='airflow_poc_task', 
    python_callable=print_hello, 
    dag=dag)

dummy_operator >> hello_operator