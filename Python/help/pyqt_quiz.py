import sys
from PyQt5.QtWidgets import (
    QApplication,
    QWidget,
    QVBoxLayout,
    QLabel,
    QPushButton,
    QMessageBox,
    QProgressBar,
    QFrame,
)
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QFont, QPalette, QColor


class PsychologyTestApp(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

        self.questions = [
            {
                "question": "당신은 새로운 사람들과 어울리는 것을 좋아하나요?",
                "yes": 2,
                "no": -2,
            },
            {
                "question": "계획을 세우기보다는 즉흥적으로 행동하나요?",
                "yes": 1,
                "no": -1,
            },
            {"question": "혼자 있는 시간을 즐기는 편인가요?", "yes": -2, "no": 2},
            {"question": "모임에서 대화를 먼저 시작하는 편인가요?", "yes": 2, "no": -2},
            {
                "question": "책 읽기나 영화 감상 같은 조용한 활동을 선호하나요?",
                "yes": -1,
                "no": 1,
            },
            {
                "question": "많은 사람들 앞에서 발표하는 것을 즐기나요?",
                "yes": 2,
                "no": -2,
            },
            {
                "question": "새로운 환경에 적응하는 것이 어렵게 느껴지나요?",
                "yes": -1,
                "no": 1,
            },
        ]

        self.current_question_index = 0
        self.score = 0
        self.show_next_question()

    def initUI(self):
        self.setStyleSheet(
            """
            QWidget {
                background-color: #f0f0f0;
            }
            QLabel {
                color: #2c3e50;
                padding: 10px;
            }
            QPushButton {
                background-color: #3498db;
                color: white;
                border: none;
                padding: 10px;
                min-width: 100px;
                border-radius: 5px;
                margin: 5px;
            }
            QPushButton:hover {
                background-color: #2980b9;
            }
        """
        )

        self.layout = QVBoxLayout()
        self.layout.setSpacing(15)
        self.layout.setContentsMargins(20, 20, 20, 20)

        # 진행 상황 표시
        self.progress_bar = QProgressBar(self)
        self.progress_bar.setStyleSheet(
            """
            QProgressBar {
                border: 2px solid #bdc3c7;
                border-radius: 5px;
                text-align: center;
            }
            QProgressBar::chunk {
                background-color: #27ae60;
            }
        """
        )
        self.layout.addWidget(self.progress_bar)

        # 질문 레이블
        self.question_label = QLabel("", self)
        self.question_label.setFont(QFont("Arial", 12, QFont.Bold))
        self.question_label.setWordWrap(True)
        self.question_label.setAlignment(Qt.AlignCenter)
        self.layout.addWidget(self.question_label)

        # 버튼 프레임
        button_frame = QFrame()
        button_layout = QVBoxLayout()

        self.yes_button = QPushButton("예", self)
        self.yes_button.clicked.connect(lambda: self.answer_question("yes"))
        button_layout.addWidget(self.yes_button)

        self.no_button = QPushButton("아니오", self)
        self.no_button.clicked.connect(lambda: self.answer_question("no"))
        button_layout.addWidget(self.no_button)

        button_frame.setLayout(button_layout)
        self.layout.addWidget(button_frame)

        self.setLayout(self.layout)
        self.setWindowTitle("성격 유형 테스트")
        self.setGeometry(100, 100, 500, 300)

    def show_next_question(self):
        if self.current_question_index < len(self.questions):
            # 진행률 업데이트
            progress = (self.current_question_index / len(self.questions)) * 100
            self.progress_bar.setValue(int(progress))

            question_data = self.questions[self.current_question_index]
            self.question_label.setText(
                f"Q{self.current_question_index + 1}. {question_data['question']}"
            )
        else:
            self.show_result()

    def answer_question(self, answer):
        if self.current_question_index < len(self.questions):
            self.score += self.questions[self.current_question_index][answer]
            self.current_question_index += 1
            self.show_next_question()

    def show_result(self):
        result_text = f"총 점수: {self.score}\n\n"

        if self.score >= 4:
            result_text += "당신은 매우 외향적인 성향이 강합니다!\n\n"
            result_text += "• 사교적이고 활동적인 성격\n"
            result_text += "• 다양한 사람들과의 교류를 즐김\n"
            result_text += "• 에너지가 넘치고 모험을 즐기는 성향"
        elif self.score >= 1:
            result_text += "당신은 약간 외향적인 성향입니다.\n\n"
            result_text += "• 사회적 활동을 즐기지만 적절한 휴식도 필요\n"
            result_text += "• 상황에 따라 유연하게 대처 가능\n"
            result_text += "• 균형 잡힌 사교성을 보유"
        elif self.score >= -1:
            result_text += "당신은 균형 잡힌 성격을 가지고 있습니다!\n\n"
            result_text += "• 상황에 따라 내향적/외향적 성향을 적절히 조절\n"
            result_text += "• 다양한 환경에 잘 적응\n"
            result_text += "• 안정적이고 유연한 성격의 소유자"
        elif self.score >= -4:
            result_text += "당신은 약간 내향적인 성향입니다.\n\n"
            result_text += "• 조용한 환경에서 더 편안함을 느낌\n"
            result_text += "• 깊이 있는 관계를 선호\n"
            result_text += "• 신중하고 사려 깊은 성격"
        else:
            result_text += "당신은 매우 내향적인 성향이 강합니다!\n\n"
            result_text += "• 독립적이고 자기주도적인 성격\n"
            result_text += "• 깊이 있는 사고와 분석을 즐김\n"
            result_text += "• 혼자만의 시간을 통해 에너지를 충전"

        reply = QMessageBox(self)
        reply.setWindowTitle("테스트 결과")
        reply.setText(result_text)
        reply.setStandardButtons(QMessageBox.Reset | QMessageBox.Close)
        reply.setStyleSheet(
            """
            QMessageBox {
                background-color: #f0f0f0;
            }
            QPushButton {
                background-color: #3498db;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
            }
            QPushButton:hover {
                background-color: #2980b9;
            }
        """
        )

        result = reply.exec_()

        if result == QMessageBox.Reset:
            self.restart_test()
        else:
            self.close()

    def restart_test(self):
        self.current_question_index = 0
        self.score = 0
        self.progress_bar.setValue(0)
        self.show_next_question()


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = PsychologyTestApp()
    window.show()
    sys.exit(app.exec_())
