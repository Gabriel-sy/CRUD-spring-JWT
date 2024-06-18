document.getElementById('form').addEventListener('submit', function(event){
    event.preventDefault();

    const formData = {
        login: document.getElementById('login').value,
        password: document.getElementById('password').value
    }

    fetch('http://localhost:8080/auth/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if(response.ok){
            alert("Usuário Registrado com sucesso!");
            window.location.href = "http://localhost:8080/auth/login";
        } else {
            alert("Erro ao ao registrar usuário");
        }
    })
    .catch(error => {
        console.error('Erro: ' + error);
        alert("Erro ao registrar usuário");
    })
})