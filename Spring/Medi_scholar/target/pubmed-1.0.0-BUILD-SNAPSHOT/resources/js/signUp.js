function idCheck(){
	const userid = document.querySelector('#userId').value;
	const idCheckMessage = document.querySelector('#idcheckmessage');
	
    // 이메일 형식인지 확인하는 정규표현식
	const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

	if (!emailPattern.test(userid)) {
		idCheckMessage.innerText = '올바른 이메일 형식이 아닙니다.';
		document.querySelector('#userId').focus();
		return; // 이메일 형식이 아니면 함수 종료
	}else{
		document.querySelector('#idcheckmessage').innerText = '사용가능한 ID 입니다.';
	}
	fetch('/member/idcheck', {
		method:'POST',
		headers:{
				'Content-Type':'application/json',
				},
		body : JSON.stringify({ userid: userid })
	})
	.then(response => response.json())
	.then(data =>{
				if(data.result=="1"){
					document.querySelector('#idcheckmessage').innerText = '이미 존재하는 ID 입니다.';
					document.querySelector('#userId').focus();
				}else{
					document.querySelector('#idcheckmessage').innerText = '사용가능한 ID 입니다.';
				}
	})
	.catch((error)=>{
					console.error('전송실패:', error);
	});
}

function passCheck(){
	const pass = document.querySelector('#passWord').value;
	const regex = /^(?=.*[!@#$%^&*]).{8,}$/;

	console.log(pass);

	if(!regex.test(pass)){
		document.querySelector("#passcheckmessage").innerText ='8자리 이상 특수문자가 포함되어야합니다.';
	}else{
		document.querySelector("#passcheckmessage").innerText ='';
	}
}

function nameCheck(){
	const name = document.querySelector('#name').value;

	if(name.length<2 || name == null){
		document.querySelector('#namecheckmessage').innerText = '이름을 2글자 이상 입력해주세요'
	}else {
	document.querySelector('#namecheckmessage').innerText = '';
	}
}

function signUpsubmit(){
	const userid = document.querySelector('#userId').value;
	const pass = document.querySelector('#passWord').value;
	const name = document.querySelector('#name').value;
	let nation = document.querySelector('#nationVal').value;
	let job = document.querySelector('#jobVal').value;
	let major = document.querySelector('#major').value;
	let interest = document.querySelector('#interest').value;

	console.log(userid);
	console.log(pass);
	console.log(name);
	console.log(nation);
	console.log(job);
	console.log(major);
	console.log(interest);

	fetch('/member/signupchk', {
		method:'POST',
		headers:{
				'Content-Type':'application/json',
		},
		body : JSON.stringify({ userid: userid, userpw: pass, usernm: name, nation:nation, job:job, major:major, interest:interest })
	})
	.then(response => response.json())
	.then(data =>{
		if(data.status === "success"){
		window.location.href='/member/login';
		}
	})
	.catch((error)=>{
		console.error('전송실패:', error);
	});
}

function passCheck(){
	const pass = document.querySelector('#passWord').value;
	const regex = /^(?=.*[!@#$%^&*]).{8,}$/;

	console.log(pass);

	if(!regex.test(pass)){
		document.querySelector("#passcheckmessage").innerText ='8자리 이상 특수문자가 포함되어야합니다.';
	}else{
		document.querySelector("#passcheckmessage").innerText ='';
	}
}
function loadInterest() {
    let primary = document.getElementById('major').value;
    console.log(primary);

    fetch('/member/interest', {
        method: 'POST', 
        headers: {
            'Content-Type': 'application/json', 
        },
        body: JSON.stringify({ primary: primary })
    })
    .then(response => response.json())
    .then(iList => {
    // Select Box에 데이터 채우기
    fillSelectBox(iList);
})
.catch(error => console.error('Error:', error));
}

// Select Box 채우는 함수
function fillSelectBox(iList) {
    let selectBox = document.getElementById('interest');
    iList.forEach(item => {
        let option = document.createElement('option');
        option.value = item.ci;
        option.text = item.codeko;
        selectBox.appendChild(option);
    });
}

    function redirectToGoogle() {
        // 서버로부터 reqUrl 주소를 가져오는 비동기 요청
        fetch('/api/v1/oauth2/google', {
            method: 'POST',
        })
        .then(response => response.text())
        .then(reqUrl => {
            // reqUrl로 이동
            window.location.href = reqUrl;
        })
        .catch(error => {
            console.error('Error fetching reqUrl:', error);
        });
    }
