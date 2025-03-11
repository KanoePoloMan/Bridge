// Функция для загрузки списка друзей
function loadFriends() {
    fetch('/friends')
        .then(response => response.json())
        .then(data => {
            const friendsList = document.getElementById('friends-list');
            friendsList.innerHTML = '<h2>Друзья</h2>';
            if (data.length > 0) {
                data.forEach(friend => {
                    const button = document.createElement('button');
                    button.textContent = friend.name;
                    friendsList.appendChild(button);
                });
            }
        })
        .catch(error => console.error('Ошибка загрузки друзей:', error));
}

// Функция для загрузки списка серверов
function loadServers() {
    fetch('/servers')
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
window.onload = function() {
    loadFriends();
    loadServers();
};

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