const token = document.querySelector('#token');

const loginForm = document.querySelector('#loginForm');




loginForm.addEventListener('submit',
    function (e) {
        e.preventDefault();
        const xhr = new XMLHttpRequest();
        xhr.responseType = 'json';
        xhr.onload = function () {
            const code = xhr.status;
            Window.sessionStorage
            if (code == 200) {
                const datas = xhr.response;
                token.value = datas.access_token;
                localStorage.setItem('access_token', datas.access_token);
            } else if (code == 400) {
                alert(xhr.response.error_description);
            } else {
                alert('Other error: ', code)
            }
        }
        xhr.onerror = function () {
        }
        xhr.open('POST', 'http://localhost:9090/oauth/token');


        let data = "username=" + document.querySelector('#username').value;
        data += "&password=" + document.querySelector('#password').value;
        data += "&client_id=" + document.querySelector('#clientId').value;
        data += "&grant_type=" + document.querySelector('#grantType').value;

        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send(data);

    },
    false
);
//const publicHello = document.querySelector('#publicHello');
const links = document.querySelectorAll('#publicHello, #userInfo, #privateHelloUser, #privateHelloAdmin, #privateAuthenticated');
const base_url = 'http://localhost:9090';

links.forEach(link => {
    link.addEventListener('click', _e => {
        const xhr = new XMLHttpRequest();
        xhr.onload = () => {
            const st = xhr.status;
            if (st == 200) {
                console.log(xhr.response);
            } else if (st == 400) {
                alert('status 400 bad request' + xhr.response.error_description);
            } else {
                alert('Bad error, status code: ' + st)
            }
        }
        xhr.onerror = () => { alert('XHR error') };
        xhr.open('GET', base_url + link.text);
        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('access_token'));
        xhr.send(null);

    }
    );
})



