

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>

            function updateQuestionContent(data) {
                var questionIndexContainer = document.querySelector("#pills-tab");
                var questionTextContainer = document.querySelector("#pills-tabContent");
                var questionNav = document.querySelector("#question-nav");
                var questionElements = [];
                for (let i = 0; i < data.length; i++) {
                    var questionIndex = "Q" + (i + 1);
                    var questionText = data[i].questionText;

                    var tabElement = document.createElement("li");
                    tabElement.classList.add("nav-item");

                    var buttonElement = document.createElement("button");
                    buttonElement.classList.add("nav-link", "btn", "btn-sm");
                    buttonElement.textContent = questionIndex;
                    buttonElement.setAttribute("id", "pills-" + i + "-tab");
                    buttonElement.setAttribute("data-bs-toggle", "pill");
                    buttonElement.setAttribute("data-bs-target", "#pills-" + i);
                    buttonElement.setAttribute("type", "button");
                    buttonElement.setAttribute("role", "tab");
                    buttonElement.setAttribute("aria-controls", "pills-" + i);
                    buttonElement.setAttribute("aria-selected", i === 0 ? "true" : "false");


                    var questionElement = document.createElement("div");
                    questionElement.classList.add("tab-pane", "fade");
                    questionElement.setAttribute("id", "pills-" + i);
                    questionElement.setAttribute("role", "tabpanel");
                    questionElement.setAttribute("aria-labelledby", "pills-" + i + "-tab");
                    questionElement.textContent = questionText;


                    tabElement.appendChild(buttonElement);
                    questionIndexContainer.appendChild(tabElement);
                    questionTextContainer.appendChild(questionElement);
//                    var questionInfo = {
//                        tabElement: tabElement,
//                        questionElement: questionElement
//                    };
//
//                    questionElements.push(questionInfo);

                    updateQuestionAnswer(data[i], questionElement);
                }
//                updateQuestionNavigation(questionElements, questionNav);
            }
//            function updateQuestionNavigation(questionElements, questionNav) {
//                var totalQuestions = questionElements.length;
//
//                if (totalQuestions <= 1) {
//                    questionNav.style.display = 'none';
//                } else {
//                    questionNav.style.display = 'block';
//
//                    var currentQuestionIndex = 0;
//
//                    function updateNavigationButtons() {
//                        var prevButton = document.getElementById('prev-question');
//                        var nextButton = document.getElementById('next-question');
//
//                        prevButton.disabled = currentQuestionIndex === 0;
//                        nextButton.disabled = currentQuestionIndex === totalQuestions - 1;
//                    }
//
//                    updateNavigationButtons();
//
//                    document.getElementById('prev-question').addEventListener('click', function () {
//                        if (currentQuestionIndex > 0) {
//                            currentQuestionIndex--;
//                            questionElements[currentQuestionIndex].tabElement.querySelector('button').click();
//                            updateNavigationButtons();
//                        }
//                    });
//
//                    document.getElementById('next-question').addEventListener('click', function () {
//                        if (currentQuestionIndex < totalQuestions - 1) {
//                            currentQuestionIndex++;
//                            questionElements[currentQuestionIndex].tabElement.querySelector('button').click();
//                            updateNavigationButtons();
//                        }
//                    });
//                }
//            }
