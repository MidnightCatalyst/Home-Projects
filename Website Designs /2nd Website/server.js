const express = require('express'); //Express js is use to create server
const path = require('path'); //path allow us to know uor html, css file location
const bodyParser = require('body-parser'); //body-parser is use to parse the data from the form, allowing us to send and receive data
const knex = require('knex'); // knex is use to connect with database

const db = knex({ //connect to database
    client: 'pg', //use postgres
    connection: { //set the connection
        host: '127.0.0.0.1', //Database host
        user: 'postgres', //your user psql username
        password: 'project', //your user's psql password
        database: 'loginforwebsite2' //database name
    }
});

const app = express(); //creates a server with express

let initialPath = path.join(__dirname, 'public'); //initial path of our html, css file
app.use(bodyParser.json()); //use to parse the data from the form
app.use(express.static(initialPath)); //use to set the initial path where the html files are at, within public folder.

app.get('/', (req, res) => { //get request to the root of the server (home page)
    res.sendFile(path.join(initialPath, 'index.html')); //send the index.html file
})

app.get('/login', (req, res) => { //get request to the about page
    res.sendFile(path.join(initialPath, 'login.html'))
})

app.get('/register', (req, res) => { //get request to the about page
    res.sendFile(path.join(initialPath, 'register.html'))
})

app.post('/register-user', (req, res) => {
    const { name, email, password } = req.body;

    if(!name.length || !email.length || !password.length){
        res.json('fill all the fields');
    } else{
        db("users").insert({
            name: name,
            email: email,
            password: password
        })
        .returning(["name", "email"])
        .then(data => {
            res.json(data[0])
        })
        .catch(err => {
            if(err.detail.includes('already exists')){
                res.json('email already exists');
            }
        })
    }
})

app.post('/login-user', (req, res) => {
    const { email, password } = req.body;

    db.select('name', 'email')
    .from('users')
    .where({
        email: email,
        password: password
    })
    .then(data => {
        if(data.length){
            res.json(data[0]);
        } else{
            res.json('email or password is incorrect');
        }
    })
})

app.listen(3000, (req, res) => {
    console.log('listening on port 3000......')
})

