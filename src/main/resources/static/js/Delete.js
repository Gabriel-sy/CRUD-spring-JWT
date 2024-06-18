
    document.addEventListener('DOMContentLoaded', () => {
        const deleteButton = document.getElementById('delete-button');
        
        deleteButton.addEventListener('click', async () => {
            const noteId = deleteButton.getAttribute('data-id');
            const confirmed = confirm('Tem certeza de que quer deletar?');

            if (confirmed) {
                try {
                    const response = await fetch(`http://localhost:8080/home/api/delete/${noteId}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });

                    if (response.ok) {
                        alert('Nota deletada com sucesso!');
                        document.querySelector('.note-preview').remove();
                        window.location.href = "http://localhost:8080/home/list"
                    } else {
                        alert('Erro ao deletar a nota.');
                    }
                } catch (error) {
                    console.error('Erro ao deletar:', error);
                    alert('Erro ao deletar a nota.');
                }
            }
        });
    });
