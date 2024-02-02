
function createFrom(obj){
	if(obj.email.value ==""){
		alert("사용하실 이메일을 넣어주세요.");
		obj.email.focus();
		return false;
	}
	
	if(obj.pass.value ==""){
		alert("패스워드를 넣어주세요 (8자리 이상)");
		obj.pass.focus();
		return false;
	}
	
	if(obj.pass.value.length < 8){
		alert("패스워드는 8글자 이상으로 만들어주세요.");
		obj.pass.focus();
		return false;
	}
	/*
	if(obj.passwordCheck.value ==""){
		alert("비밀번호 확인란에 입력해주세요.");
		obj.passwordCheck.focus();
		return false;
	}
	
	if(obj.password.value != obj.passwordCheck.value){
		alert("입력하신 비밀번호가 같지 않습니다.");
		obj.passwordCheck.focus();
		return false;
	}
	*/
	
	if(obj.name.value ==""){
		alert("이름을 넣어주세요.");
		obj.name.focus();
		return false;
	}
	
	if(obj.nation.value ==""){
		alert("국가를 선택해주세요.");
		obj.nation.focus();
		return false;
	}
	
	if(obj.job.value ==""){
		alert("직업을 선택해주세요.");
		obj.job.focus();
		return false;
	}
	
}
function idCheck(){	//id check ajax
	let inputid=document.getElementById("userId").value;
	console.log(inputid);
	
	$.ajax({
		type:"POST",
		url:"/member/idcheck",
		data:{pid:inputid},
		success:function(resp){
			if(resp>=1){
				$("#result_message").html("이미 사용중인 ID입니다.")
			}else{
				$("#result_message").html("사용 가능한 ID입니다.")
			}
		}
	})
}
/*
function idCheck(obj, root){
	alert(obj.id.value);
	
	if(obj.id.value ==""){
		alert("아이디를 반드시 입력하세요.");
		obj.id.focus();
		return false;
	}else{
		let url = root + "/user/idCheck?id=" + obj.id.value;
		window.open(url, "", "width=400, height=200");
	}
}
*/