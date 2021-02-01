from flask import Flask, request

app = Flask(__name__)

@app.route("/approval")
def approval(   ):
    response = request.args.get('approval')
    # Update DB Value
    # TODO Assuming we have updated the database    
    return '''<h1>The Manager selection is: {}</h1>'''.format(response)

# Assuming we are making a REST Call to database and fetch the record
@app.route("/checkApproval")
def checkAppproval(   ):
    return "True"


if __name__ == '__main__':
    app.run(debug=True)
