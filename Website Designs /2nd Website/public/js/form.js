//form loading animation

const form = [...document.querySelector('.form').children]; //select the form element

form.forEach((item, i) => { //loop through the form element
    setTimeout(() => { //set a timeout function
        item.style.opacity = 1;
    }, i * 100); //set the opacity to 1 after 1 second
})

window.onload = () => {
    if(sessionStorage.name){
        location.href = '/';
    }
}

//form validation 

const name = document.querySelector('.name') || null; //select the name input
const email = document.querySelector('.email'); //select the email input
const password = document.querySelector('.password'); //select the password input
const submitBtn = document.querySelector('.submit-btn'); //select the password2 input

if(name == null){ // means login page is open
    submitBtn.addEventListener('click', () => {
        fetch('/login-user',{
            method: 'post',
            headers: new Headers({'Content-Type': 'applications/json'}),
            body: JSON.stringify({
                email: email.value,
                password: password.value
            })
        })
        .then(res => res.json())
        .then(data => {
            if(data.name)
            {
                alert('login successful');
            }
            else{
                alert(data);
            }
        })
    })
} else{ //means register page is open
    submitBtn.addEventListener('click', () => { //add event listener to the submit button
        fetch('/register-user', { //fetch the register route
            method: 'post', //set the method to post
            headers: new Headers({'Content-Type': 'application/json'}), //set the header
            body: JSON.stringify({ //set the body
                name: name.value,
                email: email.value,
                password: password.value
            })
        })
        .then(res => res.json())
        .then(data => {
            validateData(data);
        })
    })

}

const validateData = (data) => {
    if(!data.name){
        alertBox(data);
    } else{
        sessionStorage.name = data.name;
        sessionStorage.email = data.email;
        location.href = '/';
    }
}

const alertBox = (data) => {
    const alertContainer = document.querySelector('.alert-box');
    const alertMsg = document.querySelector('.alert');
    alertMsg.innerHTML = data;

    alertContainer.style.top = `5%`;
    setTimeout(() => {
        alertContainer.style.top = null;
    }, 5000);
}
