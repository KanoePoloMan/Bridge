// Функция для загрузки списка друзей
function loadFriends() {
    const accessToken = localStorage.getItem("accessToken");
    fetch('/friends/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        const friendsList = document.getElementById('friends-list');
        friendsList.innerHTML = '<h2>Друзья</h2>';
        if (data.length > 0) {
            data.forEach(friend => {
                const button = document.createElement('button');
                button.textContent = friend.friendName + " " + friend.state;
                friendsList.appendChild(button);
            });
        }
    })
    .catch(error => console.error('Ошибка загрузки друзей:', error));
    fetch('/friends/invitedList', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        const friendsList = document.getElementById('friends-list');
        friendsList.innerHTML = '<h2>Друзья</h2>';
        if (data.length > 0) {
            let index = 0;
            data.forEach(friend => {
                const button = document.createElement('button');
                button.textContent = friend.friendName + " " + friend.state;
                button.addEventListener("click", onFriendButtonClick);
                friendsList.appendChild(button);
                index++;
            });
        }
    })
    .catch(error => console.error('Ошибка загрузки друзей:', error));
}
function onFriendButtonClick() {
    let string = this.textContent;
    if(string.includes("INVITE")) {
        localStorage("loadPageMenu", "/friendRequest/" + string.replace(" INVITE", ""));
    } else {
        // загрузка чата
    }
}

// Функция для загрузки списка серверов
function loadServers() {
    const accessToken = localStorage.getItem("accessToken");
    fetch('/servers/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }   
    })
    .then(response => response.json())
    .then(data => {
        const serversList = document.getElementById('servers-list');
        serversList.innerHTML = '<h2>Серверы</h2>';
        if (data.length > 0) {
            data.forEach(server => {
                const button = document.createElement('button');
                button.textContent = server.name;
                serversList.appendChild(button);
            });
        }
    })
    .catch(error => console.error('Ошибка загрузки серверов:', error));
}

// Загрузка данных при загрузке страницы

loadFriends();
loadServers();


// Обработка загрузки HTML файла
document.getElementById('html-upload').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const content = e.target.result;
            document.querySelector('.content').innerHTML = content;
        };
        reader.readAsText(file);
    }
});
// Обработка нажатия на кнопку "+" для друзей
document.getElementById('add-friend-btn').addEventListener('click', function() {
    document.getElementById('friend-input-group').style.display = 'flex';
});

// Обработка нажатия на кнопку "+" для серверов
document.getElementById('add-server-btn').addEventListener('click', function() {
    document.getElementById('server-input-group').style.display = 'flex';
});

// Функция для отправки приглашения другу
function sendFriendInvite() {
    const friendName = document.getElementById('friend-input').value;
    const accessToken = localStorage.getItem("accessToken");
    fetch('/friends/invite', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: friendName }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Успешно:', data);
    })
    .catch((error) => {
        console.error('Ошибка:', error);
    });
}

// Функция для входа на сервер
function joinToServer() {
    const serverCode = document.getElementById('server-input').value;
    const accessToken = localStorage.getItem("accessToken");
    fetch('/servers/join', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ code: serverCode }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Успешно:', data);
    })
    .catch((error) => {
        console.error('Ошибка:', error);
    });
}

// Функция для загрузки страницы создания сервера
function loadCreatePage() {
    localStorage.setItem('loadPageMenu', '/createServerPage');
}
function checkUpdateMenu() {
    if(localStorage.getItem('updatedMenu') == 'false') {
        const container = document.getElementById('content');
        const html = localStorage.getItem("htmlMenu");

        container.innerHTML = html;

        const scripts = container.querySelectorAll('script');
        scripts.forEach(script => {
            const newScript = document.createElement('script');

            // Если скрипт внешний (имеет src)
            if (script.src) {
                newScript.src = script.src;
            }
            // Если скрипт встроенный (имеет текст)
            else {
                newScript.textContent = script.textContent;
            }

            // Добавляем скрипт в документ
            document.body.appendChild(newScript);
        });

        localStorage.setItem("updatedMenu", true);
    }
}
function loadPageMenu() {
    if(localStorage.getItem("loadPageMenu") != "none") {
        const URI = localStorage.getItem("loadPageMenu");
        const accessToken = localStorage.getItem("accessToken");

        fetch(localStorage.getItem("serverURL") + URI, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.text(); // Обрабатываем ответ как JSON
        })
        .then(html => {
            localStorage.setItem('updatedMenu', false);
            localStorage.setItem('htmlMenu', html);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Ошибка:', error);
            document.getElementById('content').innerHTML = '<p style="color: red;">Не удалось загрузить контент.</p>';
        });

        localStorage.setItem("loadPageMenu", "none");
    }
}

setInterval(loadPageMenu, 200);
setInterval(checkUpdateMenu, 200);