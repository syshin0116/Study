const scriptName = "신비 봇";

function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    if (msg.startsWith("/")) {
        var sdcard = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

        function save(folderName, fileName, str) {
            var c = new java.io.File(sdcard + "/" + folderName + "/" + fileName);
            var d = new java.io.FileOutputStream(c);
            var e = new java.lang.String(str);
            d.write(e.getBytes());
            d.close();
        }


        while ((g = e.readLine()) != null) {
            f += "\n" + g; //\ = 역슬래쉬 → 줄바꿈 표시
        }
        c.close();
        d.close();
        e.close();
        return f.toString();
    }


    try {

        var folder = new java.io.File(sdcard + "/학습/" + room);
        folder.mkdirs();

        if (msg == "신비 교육목록") {
        }

        if (msg.indexOf("신비 교육") == 0) {
            var study0 = msg.substring(7, msg.length);
            var study1 = study0.split("=");
            var suy1 = study1[0];
            var suy2 = study1[1];

            if (suy2 == "") {
                replier.reply("틀렸어.\n 신비 교육 (가르칠 말)=(가르칠 내용) 으로 작성하라구.");
            }
            else if (suy1 == "") {
                replier.reply("틀렸어.\n 신비 교육 (가르칠 말)=(가르칠 내용) 으로 작성하라구.");
            }
            else {
                replier.reply(suy1 + "을(를) " + suy2 + "(으)로 알고 있을게.");
                folder.mkdirs();
                save("학습/" + room, suy1.trim() + ".txt", suy2);
            }

        }

        var talk = read("학습/" + room, msg + ".txt");
        if (talk !== null) {
            replier.reply(talk);
        }


        if (msg.indexOf("신비 제거") == 0) {
            replier.reply(msg.substr(7) + "의 학습내용 : " + read("학습/" + room, msg.substr(7) + ".txt"));
            new java.io.File("sdcard/학습/" + room + "/" + msg.substr(7) + ".txt").delete();
            replier.reply(msg.substr(7) + "은(는) 취소할게!");
        }

    } catch (e) {
        Api.replyRoom("이은비", "[ 대화학습봇 오류발생🚨  ]\n\n오류 이름: " + e.name + "\n오류 메시지: " + e.message + "\n오류 위치: " + e.lineNumber);
    }
}


function read(folderName, fileName) {
    var b = new java.io.File(sdcard + "/" + folderName + "/" + fileName);
    if (!(b.exists())) return null;
    var c = new java.io.FileInputStream(b);
    var d = new java.io.InputStreamReader(c);
    var e = new java.io.BufferedReader(d);
    var f = e.readLine();
    var g = "";
}


//아래 4개의 메소드는 액티비티 화면을 수정할때 사용됩니다.
function onCreate(savedInstanceState, activity) {
    var textView = new android.widget.TextView(activity);
    textView.setText("Hello, World!");
    textView.setTextColor(android.graphics.Color.DKGRAY);
    activity.setContentView(textView);
}

function onStart(activity) { }

function onResume(activity) { }

function onPause(activity) { }

function onStop(activity) { }