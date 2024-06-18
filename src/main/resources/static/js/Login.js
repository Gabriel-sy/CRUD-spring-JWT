document.getElementById('form').addEventListener('submit', function(event){
    event.preventDefault();

    const formData = {
        login: document.getElementById('login').value,
        password: document.getElementById('password').value
    }

    fetch('http://localhost:8080/auth/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if(response.ok){
            alert("Acesso bem-sucedido!");
            window.location.href = "http://localhost:8080/home/list";
        } else {
            alert("Erro ao ao acessar conta, tente novamente");
        }
    })
    .catch(error => {
        console.error('Erro: ' + error);
        alert("Erro ao ao acessar conta, tente novamente");
    })
})