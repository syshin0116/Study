 $(document).ajaxStart(function() {
	// 로딩 바 이미지를 띄우고 
	$('#load').show();
    // 화면의 중앙에 위치하도록 top, left 조정 (화면에 따라 조정 필요)
    $("#hiddenDivLoading").show().css({
    	top: $(document).scrollTop()+ ($(window).height() )/2.6 + 'px',
        left: ($(window).width() )/2.6 + 'px'
  	});
    // ajax 통신이 종료되었을 때 실행될 이벤트
}).ajaxStop(function() {
	// 로딩 바 태그와 이미지를 모두 hide 처리
	$('#load').hide();
    $("#hiddenDivLoading").hide();
});

function getComma(value){
	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}