<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doing exam</title>
    <jsp:include page="common/header.jsp" />
</head>

<body>

    <button onclick="prevQuestion()">Prev</button>
    <button onclick="nextQuestion()">Next</button>
    <span>
        Time remaining: <span id="time-remaining-span"></span>
    </span>
    <button onclick="submitExam()">Submit</button><br>

    <div id="question-buttons">
        
    </div>

    <span class="fs-3">
        <span class="fs-3" id="question-index"></span>: 
        <span class="fs-3" id="question-text"></span>
    </span><br>

    <div id="answers">

    </div>
    <button id="uncheck-button">Uncheck current choices</button>

    <script>
        var timeRemaining = ${duration};

        var questionIndex = 1;
        var questionsSize = ${questionsSize};

        var questionData;

        $( document ).ready(function() {
            function updateTimer() {
                var minutes = Math.floor(timeRemaining / 60);
                var seconds = timeRemaining % 60;
                var formattedTime = minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
                $("#time-remaining-span").text(formattedTime);
                if (timeRemaining > 0) {
                    timeRemaining--;
                    setTimeout(updateTimer, 1000);
                } else {
                    submitExam();
                }
            }

            updateTimer();

            var questionButtons = $("#question-buttons");
            for (var i = 1; i <= questionsSize; i++) {
                var button = $("<button>")
                    .text(i)
                    .on("click", function() {
                        submitCurrentChoices();
                        questionIndex = $(this).index() + 1;
                        showQuestionAtCurrentIndex();
                    });
                questionButtons.append(button);
            }

            showQuestionAtCurrentIndex();

            setInterval(submitCurrentChoices, 30000);

            $("#uncheck-button").on("click", function(){
                $("input[type=radio]").prop("checked", false);
                $("input[type=checkbox]").prop("checked", false);
            });
        });

        function nextQuestion() {
            submitCurrentChoices();
            if (questionIndex == questionsSize) {
                questionIndex = 1;
            } else {
                questionIndex++;
            }
            showQuestionAtCurrentIndex();
        }
 
        function prevQuestion() {
            submitCurrentChoices();
            if (questionIndex == 1) {
                questionIndex = questionsSize;
            } else {
                questionIndex--;
            }
            showQuestionAtCurrentIndex();
        }

        function submitExam() {
            submitCurrentChoices();
            $.post("attempt", { 
                "action": "submitExam",
                "timeRemaining": timeRemaining
            }, function(data) {
                alert('submitted exam')
            });
        }

        // submitted with time remaining
        function submitCurrentChoices() {
            var selectedAnswers = [];   
            $('input:checked').each(function() {
                var value = $(this).val();
                selectedAnswers.push(parseInt(value, 10));
            });

            var selectedChoicesJSON = {
                questionId: questionData.questionId,
                selectedAnswerIds: selectedAnswers
            };

            $.post("attempt", { 
                "action": "submitChoices",
                "selectedChoicesJSON": JSON.stringify(selectedChoicesJSON),
                "timeRemaining": timeRemaining
            }, function(data) {

            }, "json");
        }

        function updateQuestionText() {
            $('#question-index').text('Question ' + questionData.questionIndex)
                .addClass('fs-2 fw-bold');
            $('#question-text').text(questionData.questionText)
                .addClass('fs-2');
        }

        function updateQuestionAnswers() {
            var answersDiv = $('#answers');
            answersDiv.empty();
            for (var i = 0; i < questionData.answers.length; i++) {
                var answer = questionData.answers[i];

                var input = $('<input>')
                    .attr('type', questionData.questionType === 0 ? "radio" : "checkbox")
                    .attr('id', 'answer' + answer.answerId)
                    .attr('name', 'answer')
                    .attr('value', answer.answerId);

                var answerText = $('<span>')
                    .addClass('fs-4')
                    .text(answer.answerText);

                answersDiv.append(input);
                answersDiv.append(answerText);
                answersDiv.append('<br>');
            }
        }

        function markSubmittedChoices() {
            $.get("attempt", { 
                "action": "getSubmittedChoices", 
                "questionId": questionData.questionId 
            }, function(data) {
                if (data) {
                    if (questionData.questionType === 0) {
                        var selectedAnswerId = parseInt(data);
                        $('input[value="' + selectedAnswerId + '"]').prop("checked", true);
                    } else if (questionData.questionType === 1) {
                        var selectedAnswerIds = String(data).split(",").map(Number);
                        for (var i = 0; i < selectedAnswerIds.length; i++) {
                            var answerId = selectedAnswerIds[i];
                            $('input[value="' + answerId + '"]').prop("checked", true);
                        }
                    }
                }
            });
        }

        function showQuestionAtCurrentIndex() {
            $.get("attempt", { 
                "action": "getQuestionAtIndex", 
                "questionIndex": questionIndex 
            }, function(data) {
                questionData = data;
                updateQuestionText();
                updateQuestionAnswers();
                markSubmittedChoices();
            });
        }

    </script>

    <jsp:include page="common/footer.jsp" />
</body>

</html>