 function CommunityReply() {
 		//에디터 내용을 content에 담는다
        var content = tinymce.get('textarea').getContent();

		//담은 내용을 hidden 처리된 textarea에 넣고
        document.getElementById('hiddenTextarea').value = content;
	
		//form을 전송
        document.getElementById('CommunityForm').submit();
    }
    
