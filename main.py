
from flask import Flask, render_template, flash, request, url_for, redirect
import sqlite3
from flask import g
from flask import abort
import json

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
    print(cur)
    return 'Hello'


@app.route('/Users', methods=['POST'])
def Users():
    email = request.form['email']
    firstname = request.form['firstname']
    lastname = request.form['lastname']
    password = request.form['password']
    db = get_db()
    err = db.execute(
        'insert into User (email, firstname, lastname, password) values (?,?,?,?)',
        (
            request.form.get('email', type=str),
            request.form.get('firstname', type=str),
            request.form.get('lastname', type=str),
            request.form.get('password', type=str)
        )
    )
    db.commit()
    print(err)

    return email.upper()


@app.route('/food')
def get_food():
    db = get_db()
    cur = db.execute(
        'SELECT * FROM Food_Items'
    )
    rows = cur.fetchall()
    items = []
    for r in rows:
        d = {}
        d['id'] = r[0]
        d['name'] = r[1]
        d['price'] = r[2]
        d['calories'] = r[3]
        items.append(d)

    response = app.response_class(
        response=json.dumps(items),
        status=200,
        mimetype='application/json'
    )
    return response


@app.route('/order', methods=['POST'])
def add_order():
    db = get_db()
    user_id = request.form['user_id']
    item_id = request.form['item_id']
    quantity = request.form['quantity']
    amount = request.form['amount']
    delivery_address = request.form['delivery_address']

    print(user_id, quantity)

    err = db.execute(
        'insert into Orders (user_id, item_id, quantity, status, amount, delivery_address) values (?,?,?,?,?,?)',
        (
            user_id,
            item_id,
            quantity,
            "PLACED",
            amount,
            delivery_address
        )
    )
    print ("Last Inserted row is ", err.lastrowid)
    order_id = err.lastrowid
    db.commit()
    print(err)
    err = db.execute(
        'insert into Cart (user_id,order_id) values (?,?)',
        (            
            user_id,
            order_id,
        )
    )
    db.commit()
    print(err)
    return ('Order is successfully added to Cart ', order_id)


@app.route('/payment', methods=['POST'])
def add_payment():
    db = get_db()
    order_id = request.form['order_id']
    name_on_card = request.form['name_on_card']
    exp_date = request.form['exp_date']
    cvv = request.form['cvv']

    err = db.execute(
        'insert into Payment (order_id, name_on_card, exp_date, cvv) values (?,?,?,?)',
        (
            order_id,
            name_on_card,
            exp_date,
            cvv
        )
    )
    db.commit()
    print(err)

    return 'Payment completed'


@app.route('/order', methods=['GET'])
def get_orders():
    db = get_db()
    cur = db.execute(
        'SELECT * FROM Orders'
    )
    rows = cur.fetchall()
    items = []
    for r in rows:
        d = {}
        d['id'] = r[0]
        d['user_id'] = r[1]
        d['item_id'] = r[2]
        d['quantity'] = r[3]
        d['status'] = r[4]
        d['amount'] = r[5]
        d['delivery_address'] = r[6]

        items.append(d)

    response = app.response_class(
        response=json.dumps(items),
        status=200,
        mimetype='application/json'
    )
    return response

@app.route('/add_cart', methods=['POST'])
def add_cart():
    db = get_db()
    user_id = request.form['user_id']
    order_id = request.form['order_id']
    err = db.execute(
        'insert into Cart (user_id,order_id) values (?,?)',
        (            
            user_id,
            order_id,
        )
    )
    db.commit()
    print(err)
    return 'Item added to sucessfully'



@app.route('/payment', methods=['GET'])
def get_payment():
    db = get_db()
    cur = db.execute(
        'SELECT * FROM Payment'
    )
    rows = cur.fetchall()
    payments = []

    for r in rows:
        d = {}
        d['id'] = r[0]
        d['order_id'] = r[1]
        d['name_on_card'] = r[2]
        d['exp_date'] = r[3]
        d['cvv'] = r[4]
        payments.append(d)

    response = app.response_class(
        response=json.dumps(payments),
        status=200,
        mimetype='application/json'
    )
    return response


@app.route('/cart', methods=['GET'])
def get_cart():
    db = get_db()
    user_id = request.args.get('user_id')
    cur = db.execute(
        'SELECT * FROM Cart where user_id =?',(user_id) 
    )
    rows = cur.fetchall()
    cart = []
    for r in rows:
        d = {}
        d['user_id'] = r[0]
        d['order_id'] = r[1]
        cart.append(d)
    
    response = app.response_class(
        response=json.dumps(cart),
        status=200,
        mimetype='application/json'
    )
    return response

@app.route('/login', methods=['POST'])
def Login():
    db = get_db()
    email = request.form['email']
    password = request.form['password']
    cur = db.execute(
        'SELECT * FROM User WHERE email=?', (email,)
    )
    rows = cur.fetchall()
    print(rows)
    if len(rows) <= 0:
        abort(404)
    else:
        if (rows[0][2] != password):
            abort(403)
        else:
            return "Login is successful with user id:{}".format(rows[0][0])


@app.route('/logout',methods=["GET"])
def logout():
    return "Goodbye! Please login for the order"

def init_db():
    db = get_db()
    print("Initializaing database")

    with app.open_resource('schema.sql') as f:
        db.executescript(f.read().decode('utf8'))


if __name__ == '__main__':
    init_db()
    app.run(debug=True)
