<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<title>회원탈퇴</title>
<script>


function deleteform_check() {
	var pw = document.getElementById("str_Pw");

	if (pw.value == "") {
	    alert("비밀번호를을 입력하세요.");
	    document.frm.str_Pw.focus();
	    return false;
	  };
	  document.frm.submit();
	}
	
	
function checkPasswordConfirm() {
	if ($("#str_Pw").val() != $("#str_loginPw").val()) {
		alert("비밀번호 확인이 일치하지 않습니다");
		$("#str_loginPw").focus();
		return false;
	}
	return true;
}
	

}

</script>


</head>
<body>
<br>
<br>
	<center>
		<h2>알려드립니다</h2>

		<main>
			<h4>제7조(회원 탈퇴 및 자격 상실 등)</h4>
			<br>
				① 회원은 “푸르뎁”에 언제든지 탈퇴를 요청할 수 있으며 “푸르뎁”은 즉시 회원탈퇴를 처리합니다.<br /> ② 회원이
				다음 각 호의 사유에 해당하는 경우, “푸르뎁”은 회원자격을 제한 및 정지시킬 수 있습니다.<br /> 1. 가입 신청
				시에 허위 내용을 등록한 경우<br /> 2. “푸르뎁”을 이용하여 구입한 재화 등의 대금, 기타 “푸르뎁”이용에 관련하여
				회원이 부담하는 채무를 기일에 지급하지 않는 경우<br /> 3. 다른 사람의 “푸르뎁” 이용을 방해하거나 그 정보를
				도용하는 등 전자상거래 질서를 위협하는 경우<br /> 4. “푸르뎁”을 이용하여 법령 또는 이 약관이 금지하거나
				공서양속에 반하는 행위를 하는 경우<br /> ③ “푸르뎁”이 회원 자격을 제한?정지 시킨 후, 동일한 행위가 2회 이상
				반복되거나 30일 이내에 그 사유가 시정되지 아니하는 경우 “푸르뎁”은 회원자격을 상실시킬 수 있습니다.<br /> ④
				“푸르뎁”이 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 회원에게 이를 통지하고, 회원등록 말소 전에
				최소한 30일 이상의 기간을 정하여 소명할 기회를 부여합니다.
			

			<h4>${loginName}'회원님' 회원탈퇴를 진행하시려면 비밀번호를 입력하고 확인버튼을 눌러주세요</h4>
			<h5>회원 ID = ${loginName}</h5>
			</div>
			
			<form class="needs-validation" method="post" id="frm" name="frm" action="deleteForm.pulu" onsubmit="return checkPasswordConfirm()">
				<input type="text" id="str_Num" name="str_Num" value="${loginNum}">
				
				<input type="text" id="str_loginPw" name="str_loginPw" value="${loginPw}"> 
				<label for="address2" class="form-label">비밀번호</label> 
				<input type="password" class="form-control" name="str_Pw" id="str_Pw" size="28" type="text" required="required" style="max-width: max-content;">
				<br>
				<br>
				<hr class="col-3 col-md-2 mb-5" style="max-width: -webkit-fill-available;">
				<br>
				<br>
				<div style="text-align:center;" id="join_button">
				<input type="submit" name="submit" class="insert_bt" value="탈퇴하기" onsubmit="return checkPasswordConfirm()">
				<input type="button" onclick="location.href='myPage'" class="insert_bt" value="마이페이지">
				</div>
				</div>
		</main>
		</div>
	</center>
	</form>
</body>
</html>