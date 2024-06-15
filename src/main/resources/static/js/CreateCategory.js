document.getElementById('form').addEventListener('submit', function(event){
    event.preventDefault();

    const formData = {
        categoryName: document.getElementById('category').value
    }

    fetch('http://localhost:8080/home/api/create/category', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if(response.ok){
            alert("Categoria criada");
        } else {
            alert("Erro ao criar categoria");
        }
    })
    .catch(error => {
        console.error('Erro: ' + error);
        alert("Erro ao criar categoria");
    })
})