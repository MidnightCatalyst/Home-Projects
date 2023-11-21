const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');//login button in the register page
const registerLink = document.querySelector('.register-link'); //register button in the login page
const forgotLink = document.querySelector('.forgot-link'); //forgot password button
const gotoLoginLink = document.querySelector('.gotoLogin-link'); //go to login button in the register page
const btnPopup = document.querySelector('.btnLogin');
const iconClose = document.querySelector('.exit-out');


registerLink.addEventListener('click', () => 
{
    wrapper.classList.add('active');
});
loginLink.addEventListener('click', () => 
{
    wrapper.classList.remove('active');
});
forgotLink.addEventListener('click', () => 
{
    wrapper.classList.add('active1');
});
gotoLoginLink.addEventListener('click', () => 
{
    wrapper.classList.remove('active1');
});
btnPopup.addEventListener('click', () => 
{
    wrapper.classList.add('active-popup');
});
iconClose.addEventListener('click', () => 
{
    wrapper.classList.remove('active-popup');
});

ss