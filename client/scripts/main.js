const token = document.querySelector('#token');

const loginForm = document.querySelector('#loginForm');

testLocalStorage.addEventListener('clic',
    function (e) {
        if (storageAvailable('localStorage')) {
            alert('localstorage available')
        }
        else {
            alert('localstorage not available')
        }
    }
)



loginForm.addEventListener('submit',
    function (e) {
        e.preventDefault();
        const xhr = new XMLHttpRequest();
        xhr.responseText = 'json';
        xhr.onload = function () {
            const code = xhr.status;
            Window.sessionStorage
            if (code == 200) {
                accessToken.value = xhr.response.access_token
            } else if (code == 400) {
                alert('Bad request error 400: ', xhr.response.error);
            } else {
                alert('Other error: ', code)
            }
        }
        xhr.onerror = function () {
            alert('XHR error');
        }
        xhr.open('POST', 'http://localhost:9090/oauth/token');
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        let data = "username=" + document.querySelector('#username').value;
        data += "&password=" + document.querySelector('#password').value;
        data += "&client_id=" + document.querySelector('#clientId').value;
        data += "&grant_type=" + document.querySelector('#grantType').value;

        xhr.send(data);
    

    }, false);

    function storageAvailable(type) {
        try {
            var storage = window[type],
                x = '__storage_test__';
            storage.setItem(x, x);
            storage.removeItem(x);
            return true;
        }
        catch(e) {
            return e instanceof DOMException && (
                // everything except Firefox
                e.code === 22 ||
                // Firefox
                e.code === 1014 ||
                // test name field too, because code might not be present
                // everything except Firefox
                e.name === 'QuotaExceededError' ||
                // Firefox
                e.name === 'NS_ERROR_DOM_QUOTA_REACHED') &&
                // acknowledge QuotaExceededError only if there's something already stored
                storage.length !== 0;
        }
    }