import pprint
import json

from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
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
    dag_id='airflow_poc_2',
    default_args=default_args,
    start_date=datetime(2015, 12,1),
    schedule_interval=None,
    tags=['airflow_poc_2']
)

## Option 1
def run_this_func1(ds, **kwargs):
    print("Remotely received value of {} for key=employees".format(kwargs['dag_run'].conf['employees'])
)

# Option 2 when context is available
# https://github.com/apache/airflow/blob/master/airflow/example_dags/example_trigger_target_dag.py#L62
def run_this_func2(**context):
    """
    Print the payload "message" passed to the DagRun conf attribute.
    :param context: The execution context
    :type context: dict
    """
    print("Remotely received value of {} for key=message".format(context["dag_run"].conf["message"])
)



run_this = PythonOperator(
    task_id='run_this',
    provide_context=True,
    python_callable=run_this_func1,
    dag=dag,
)

# You can also access the DagRun object in templates
#bash_task = BashOperator(
#    task_id="bash_task",
#    bash_command='echo "Here is the message: $message"',
#    env={'message': '{{ dag_run.conf["message"] if dag_run else "" }}'},
#    dag=dag,
#)
