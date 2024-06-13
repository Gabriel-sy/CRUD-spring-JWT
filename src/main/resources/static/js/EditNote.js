document.getElementById('form').addEventListener('submit', function(event){
    event.preventDefault();

    const formData = {
        id: document.getElementById('id').value,
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        category: document.getElementById('category').value
    }

    fetch('http://localhost:8080/home/api/edit', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if(response.ok){
            alert("Nota editada");
        } else {
            alert("Erro ao editar nota");
        }
    })
    .catch(error => {
        console.error('Erro: ' + error);
        alert("Erro ao editar nota");
    })
})