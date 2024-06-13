document.getElementById('form').addEventListener('submit', function(event){
    event.preventDefault();

    const formData = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        category: document.getElementById('category').value
    }

    fetch('http://localhost:8080/home/api/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if(response.ok){
            alert("Nota criada");
        } else {
            alert("Erro ao criar nota");
        }
    })
    .catch(error => {
        console.error('Erro: ' + error);
        alert("Erro ao criar nota");
    })
})