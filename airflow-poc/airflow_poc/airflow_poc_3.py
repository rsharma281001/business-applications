import pprint
import json

from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator
from airflow.utils.dates import days_ago
from pprint import pprint

default_args = {
    'owner': 'RishiS',
    'depends_on_past': False,
    'start_date': datetime(2019, 2, 15),
    'email_on_failure': False,
    'email_on_retry': False,
    'provide_context': True,
}

dag = DAG(
    dag_id='airflow_poc_3',
    default_args=default_args,
    start_date=datetime(2015, 12,1),
    schedule_interval=None,
    tags=['airflow_poc_3']
)

def push(**kwargs):
    employees = kwargs['dag_run'].conf['employees']
    print(employees)
    return employees


def pull(**kwargs):
    ti = kwargs['ti']  
    fetchEmployees = ti.xcom_pull(task_ids='push')
    assert fetchEmployees == kwargs['dag_run'].conf['employees']
    print("Received value of {} for key=employees".format(fetchEmployees)
)


push = PythonOperator(
    task_id='push',
    provide_context=True,
    python_callable=push,
    dag=dag,
)

pull = PythonOperator(
    task_id='pull',
    provide_context=True,
    dag=dag,
    python_callable=pull,
)

pull << [push]
