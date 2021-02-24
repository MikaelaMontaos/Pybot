from flask import Flask, render_template, flash,request, url_for, redirect 
import sqlite3
from flask import g
from flask import abort

DATABASE = 'database.db'
app = Flask(__name__)

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()


@app.route('/')
def index():
    cur = get_db().cursor()
    print (cur)
    return 'Hello'

@app.route('/Users', methods=['POST'])
def Users():
    username = request.form['username']
    password = request.form['password']
    firstname = request.form['firstname']
    lastname = request.form['lastname']
    email = request.form['email']
    db = get_db()
    err = db.execute(
    'insert into User (username, password, firstname,lastname, email) values (?,?,?,?,?)',
    (
        request.form.get('username', type=str),
        request.form.get('password', type=str),
        request.form.get('firstname', type=str),
        request.form.get('lastname', type=str),
        request.form.get('email', type=str)
    )
    )
    db.commit()
    print(err)

    return email.upper()

@app.route('/login', methods=['POST'])
def Login():
    db = get_db()
    username = request.form['username']
    password = request.form['password']
    cur = db.execute(
        'SELECT * FROM User WHERE username=?', (username,)
    )
    rows = cur.fetchall()
    print (rows)
    if len(rows) <= 0:
        abort(404)
    else:
        if (rows[0][2] != password):
            abort(403)
        else:
            return 'Login is successful'

def init_db():
    db = get_db()
    print ("Initializaing database")

    with app.open_resource('schema.sql') as f:
        db.executescript(f.read().decode('utf8'))



if __name__ == '__main__':
    init_db()
    app.run(debug=True)

