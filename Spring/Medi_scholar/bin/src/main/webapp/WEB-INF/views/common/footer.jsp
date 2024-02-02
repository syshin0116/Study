<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

        <footer>copyright@teamproject</footer>
    </div>
    
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let today = new Date().toISOString().substr(0, 10);
            let dateInputs = document.querySelectorAll('.dateInput');
            dateInputs.forEach(function(input) {
                input.value = today;
            });
        });

        // 라디오 버튼 "SearchPeriod" 선택 시 이벤트 리스너 추가
        document.getElementById("SearchPeriod").addEventListener("change", function() {
            if (this.checked) {
                // "dateInput" 클래스를 가진 모든 요소를 활성화
                let dateInputs = document.querySelectorAll(".dateInput");
                for (let input of dateInputs) {
                    input.disabled = false;
                }
            }
        });

        // 다른 라디오 버튼을 선택했을 때 dateInput을 다시 비활성화
        let radios = document.querySelectorAll("[name='speriod']");
        for (let radio of radios) {
            if (radio.id !== "SearchPeriod") {
                radio.addEventListener("change", function() {
                    if (this.checked) {
                        let dateInputs = document.querySelectorAll(".dateInput");
                        for (let input of dateInputs) {
                            input.disabled = true;
                        }
                    }
                });
            }
        }
    </script>
    
    <script>
        document.querySelector('.menu').addEventListener('click', function() {
            const navElement = document.querySelector('nav');
            const gnbElement = document.querySelector('.gnb');
    
            if (navElement.style.display === 'none' || navElement.style.display === '') {
                navElement.style.display = 'block';
                gnbElement.style.display = 'block';
            } else {
                navElement.style.display = 'none';
                gnbElement.style.display = 'none';
            }
        });
    </script>
</body>
</html>