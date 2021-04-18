function goToBrowse(){
    let tvTrackerIframe = document.getElementById("tvTrackerIframe");
    tvTrackerIframe.src = "/browse";
}

function goToFavorites(){
    let tvTrackerIframe = document.getElementById("tvTrackerIframe");
    tvTrackerIframe.src = "/favorites";
}

function goToSignUp(){
    let tvTrackerIframe = document.getElementById("tvTrackerIframe");
    tvTrackerIframe.src = "/signup";
}

async function signIn(){
    const usernameTxt = document.getElementById("usernameTxt");
    const passwordTxt = document.getElementById("passwordTxt");

    const statusCode = await authenticate(usernameTxt.value, passwordTxt.value);

    if(statusCode === 200) {
        showSignInMessage();
        // post event into iframe
        let tvTrackerIframe = document.getElementById("tvTrackerIframe");
        tvTrackerIframe.contentWindow.postMessage('signedIn', '*');
        goToFavorites();
    }
    else if (statusCode === 401) {
        alert("Incorrect Password");
    }
    else {
        alert("Error Occurred");
    }
}

function signOut(){
    hideSignInMessage();
    window.sessionStorage.setItem("TvTrackerToken", undefined);
    window.sessionStorage.setItem("TvTrackerUsername", undefined);
    window.location.href = '/';
}

function showSignInMessage(){
    let message = "Welcome Back, " + window.sessionStorage.getItem("TvTrackerUsername") + "!";

    let signUpLink = document.getElementById("signUpLink");
    let usernameTxt = document.getElementById("usernameTxt");
    let passwordTxt = document.getElementById("passwordTxt");
    let signInBtn = document.getElementById("signInBtn");
    let signInMessage = document.getElementById("signInMessage");
    let signOutBtn = document.getElementById("signOutBtn");

    signUpLink.style.setProperty('display', 'none');
    usernameTxt.style.setProperty('display', 'none');
    passwordTxt.style.setProperty('display', 'none');
    signInBtn.style.setProperty('display', 'none');

    signInMessage.innerHTML = message;
    signInMessage.style.setProperty('display', '');
    signOutBtn.style.setProperty('display', '');
}

function hideSignInMessage(){
    let signUpLink = document.getElementById("signUpLink");
    let usernameTxt = document.getElementById("usernameTxt");
    let passwordTxt = document.getElementById("passwordTxt");
    let signInBtn = document.getElementById("signInBtn");
    let signInMessage = document.getElementById("signInMessage");
    let signOutBtn = document.getElementById("signOutBtn");

    signUpLink.style.setProperty('display', '');
    usernameTxt.style.setProperty('display', '');
    passwordTxt.style.setProperty('display', '');
    signInBtn.style.setProperty('display', '');
    signInMessage.style.setProperty('display', 'none');
    signOutBtn.style.setProperty('display', 'none');
}
