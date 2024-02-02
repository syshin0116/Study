function submitReply() {
    const replyContent = document.getElementById('replyContent').value;
    const comm_postid = document.querySelector('.postidhidden').value;

    fetch('/community/reply', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // 변경된 헤더
        },
        body: `comm_postid=${comm_postid}&replyContent=${replyContent}`, // 변경된 데이터 형식
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            refresh();
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

//댓글삭제
function deleteReply(a, b) {
	let comm_reno = a;
	let comm_writernm = b;
	
	console.log(comm_reno);
	console.log(comm_writernm);
	
fetch(`/community/delreply`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({
			reno: reno,
			user_email: user_email
		})
	})
		.then(response => response.json())
		.then(data => {
			console.log('Delete Success:', data);
			refresh();
		})
		.catch((error) => {
			console.error('Delete Error:', error);
		});
}

function submitSubreply(reno) {
    const subreplyContent = document.querySelector(`#replyInput-${reno} textarea[name='subreplyContent']`).value;
    const jnlno = document.querySelector('.jnlnohidden').value;

    fetch('/pubmed/subreply', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            jnlno: jnlno,
            subreplyContent: subreplyContent,
            reno: reno
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Sub Reply Success:', data);
        refresh();
    })
    .catch(error => {
        console.error('Sub Reply Error:', error);
    });
}


function toggleReplyInput(reno) {
    var replyInput = document.getElementById('replyInput-' + reno);
    if(replyInput.style.display === 'none') {
        replyInput.style.display = 'table-row';
    } else {
        replyInput.style.display = 'none';
    }
}


function refresh() {
    fetch(window.location.href, { method: 'GET' })
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newContent = doc.getElementById('replydiv').innerHTML;
            document.getElementById('replydiv').innerHTML = newContent;
        })
        .catch(error => console.error('Error refreshing:', error));
}