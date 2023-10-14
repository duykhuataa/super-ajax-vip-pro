<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create question</title>
        <jsp:include page="common/header.jsp" />
        <title>Questionnaire</title>
        <style>
            .container{
                max-width: 1000px;
                max-height: 300px;
                position: relative;
                background: rgb(247, 247, 247);
                border: 2px solid #000000; /* Đường viền đen 2px xung quanh container */

            }
            .question {
                margin-bottom: 10px;
            }

            <%
       Integer questionCount = (Integer) request.getAttribute("questionCounts");
       if (questionCount == null) {
           questionCount = 0;
       }
       Integer answerCount = (Integer) request.getAttribute("answerCount");
       if (answerCount == null) {
           answerCount = 0;
       }
            %>
        </style>
    </head>

    <body style="overflow-x: hidden; background-color: #f0f0f0;">
        <%@include file="common/navbar.jsp" %>

        <jsp:include page="common/sidebar.jsp" />

        <div class="p-3" style="margin-left: 250px;">

            <h1></h1>
            <div id="accordionPanelsStayOpenExample" class="accordion">
                <div class="container">
                    <div class="text-center">
                        <h1>Exam Information</h1>
                    </div>
                    <form action="exam" method="post">
                        <input type="hidden" name="action" value="exam">
                        <input type="hidden" name="examId" value="${exam.getExamId()}">
                        <input type="hidden" name="classId" value="${exam.getClassId()}">
                        <div class="row">
                            <!-- Column 1 -->
                            <div class="col-md-4">

                                <div class="form-group">
                                    <label for="examName">Exam Name:</label>
                                    <input type="text" class="form-control" id="examName" value="${exam.getExamName()}" name="examName">
                                </div>
                                <div class="form-group">
                                    <label for="examStatus">Exam Status:</label>
                                    <input type="text" class="form-control" id="examStatus" value="${exam.getStatus()}" name="status">
                                </div>
                                <div class="form-group">
                                    <label for="maxAttempts">Exam Max Attempts:</label>
                                    <input type="text" class="form-control" id="maxAttempts" value="${exam.getMaxAttempts()}"name="maxAttempts">
                                </div>
                            </div>
                            <!-- Column 2 -->
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="isDeleted">Exam Is Deleted:</label>
                                    <input type="text" class="form-control" id="isDeleted" value="${exam.getIsDeletedString()}"name="deleted">
                                </div>
                                <div class="form-group">
                                    <label for="isVisible">Exam Is Visible:</label>
                                    <input type="text" class="form-control" id="isVisible" value="${exam.getIsVisibleString()}"name="isVisible">
                                </div>
                                <div class="form-group">
                                    <label for="isDisplay">Exam Is Display:</label>
                                    <input type="text" class="form-control" id="isDisplay" value="${exam.getIsDisplayString()}"name="isDisplay">
                                </div>
                            </div>
                            <!-- Column 3 -->
                            <div class="col-md-4">

                                <div class="form-group">
                                    <label for="dateStart">Date Start:</label>
                                    <input type="text" class="form-control" id="dateStart" value="${exam.getDateStart()}"name="dateStart">
                                </div>
                                <div class="form-group">
                                    <label for="dateEnd">Date End:</label>
                                    <input type="text" class="form-control" id="dateEnd" value="${exam.getDateEnd()}"name="dateEnd">
                                </div>
                                <div class="form-group">
                                    <label for="duration">Duration:</label>
                                    <input type="text" class="form-control" id="duration" value="${exam.getDuration()}"name="duration">
                                </div>

                            </div>
                        </div>
                        <button type="submit">save</button>
                    </form></div>
                <hr>
                <form action="exam" method="post" onsubmit="return checkTotalWeight();">
                    <input type="hidden" name="action" value="question">
                    <input type="hidden" name="examId" value="${exam.getExamId()}">
                    <input type="hidden" name="classId" value="${exam.getClassId()}">

                    <div id="questionContainer">
                        <c:forEach var="q" items="${listQuestions}" varStatus="questionCount" >
                            <div class="question accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapse${questionCount.index+1}" aria-expanded="false" aria-controls="panelsStayOpen-collapse${questionCount.index+1}">
                                        Question ${questionCount.index+1}
                                    </button>
                                </h2>
                                <div id="panelsStayOpen-collapse${questionCount.index+1}" class="accordion-collapse collapse" aria-labelledby="panelsStayOpen-heading${questionCount.index+1}">
                                    <div class="accordion-body">
                                        <div class="form-check">                                   
                                            <input class="form-check-input" type="radio" name="flexRadioDefault${questionCount.index+1}" id="multichoice${questionCount.index+1}" value="multichoice" ${q.getQuestionType() eq 1 ? 'checked' :'' }>
                                            <label class="form-check-label" for="multichoice${questionCount.index+1}">Multichoice</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="flexRadioDefault${questionCount.index+1}" id="onechoice${questionCount.index+1}" value="onechoice" ${q.getQuestionType() eq 0 ? 'checked' :'' }>
                                            <label class="form-check-label" for="onechoice${questionCount.index+1}">One choice</label>
                                        </div>
                                        <p id="question${questionCount.index+1}"></p>
                                        <div class="form-group">
                                            <label for="multichoiceOption${questionCount.index+1}">Question :</label>
                                            <input type="text" class="form-control" value="${q.getQuestionText()}" style="background-color: rgb(122, 187, 243);" id="multichoiceOption${questionCount.index+1}" name="questionName${questionCount.index+1}" placeholder="Enter question">
                                            <label for="weight${questionCount.index+1}">Score :</label>
                                            <input type="number" class="form-control" value="0" style="background-color: rgb(122, 187, 243);" id="weight${questionCount.index+1}" name="weightName${questionCount.index+1}" placeholder="Nhập điểm (chưa có, chờ exam)">
                                        </div>
                                        <div id="answerId${questionCount.index+1}">
                                            <c:forEach items="${questionAnswerMap[q]}" var="a" varStatus="answerCount" >
                                                <!-- Tùy chọn cho answer -->
                                                <div class="form-group">
                                                    <label for="onechoiceOption${questionCount.index+1}-${answerCount.index+1}">Answer :</label>
                                                    <input type="text" class="form-control" value="${a.getAnswerText()}" id="onechoiceOption${questionCount.index+1}-${answerCount.index+1}" name="answerName${questionCount.index+1}-${answerCount.index+1}" placeholder="Enter answer">
                                                    <label for="weightA${questionCount.index+1}-${answerCount.index+1}">Trọng số:</label>
                                                    <input type="number" class="form-control" value="0" id="weightA${questionCount.index+1}-${answerCount.index+1}" name="weightAName${questionCount.index+1}-${answerCount.index+1}" placeholder="Nhập trọng số của câu trả lời" oninput="updateTotalWeight(${questionCount.index+1})">
                                                    <button class="btn btn-danger" onclick="removeOption(${questionCount.index+1}, ${answerCount.index+1}, true)">Remove Option</button>
                                                </div>
                                                <!-- Các tùy chọn answer khác có thể thêm tại đây -->
                                            </c:forEach></div>
                                        <button type="button" class="btn btn-primary" onclick="addOption(${questionCount.index+1})">Add Answer</button>
                                        <button class="btn btn-danger" onclick="deleteQuestion(${questionCount.index+1})">Delete Question</button>
                                        <p>Total Weight: <span id="totalWeight${questionCount.index+1}">0</span></p>
                                        <p class="text-danger" id="weightWarning${questionCount.index+1}"></p>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div><button type="submit">submit</button>
                </form>
                <button onclick="addQuestion()">Add Question</button>
            </div> </div>
            <jsp:include page="common/footer.jsp" />
        <script>
            let questionCount = <%=questionCount%>;
        </script>
        <script>

// Hàm để thêm câu hỏi mới
            function addQuestion() {

            const questionContainer = document.getElementById("questionContainer");
            const questionItems = questionContainer.querySelectorAll(".question");
            questionCount = questionItems.length + 1;
            // Tạo một câu hỏi mới
            const newQuestionItem = document.createElement('div');
            newQuestionItem.className = 'question accordion-item';
            newQuestionItem.innerHTML = `
                    <h2 class="accordion-header">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" 
                                data-bs-target="#panelsStayOpen-collapse` + questionCount + `" 
                                    aria-expanded="false" aria-controls="panelsStayOpen-collapse` + questionCount + `">
                                    Question` + questionCount + `
                                </button>
                                </h2>
                    <div id="panelsStayOpen-collapse` + questionCount + `" 
                                    class="accordion-collapse collapse"
                                    aria-labelledby="panelsStayOpen-heading` + questionCount + `">
                                <div class="accordion-body">
                            <div class="form-check">
                        <input class="form-check-input" type="radio" name="flexRadioDefault` + questionCount + `" id="multichoice` + questionCount + `" value="multichoice">
                <label class="form-check-label" for="multichoice` + questionCount + `">
                    Multichoice
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" type="radio" name="flexRadioDefault` + questionCount + `" id="onechoice ` + questionCount + `" value="onechoice" checked>
                        <label class="form-check-label" for="onechoice` + questionCount + `">
                            One choice
                                </label>
                                    </div>
                                    <p id="question` + questionCount + `"></p>
                                    <div class="form-group">
                                    <label for="multichoiceOption` + questionCount + `">Question :</label>
                                        <input type="text" class="form-control" style="background-color: rgb(122, 187, 243);;" id="multichoiceOption` + questionCount + `" name="questionName` + questionCount + `" placeholder="Enter question">
                                <label for="weight` + questionCount + `">Score :</label>
                                    <input type="number" class="form-control" value="0" style="background-color: rgb(122, 187, 243);;" id="weight` + questionCount + `" name="weightName` + questionCount + `" placeholder="Nhập điểm (chưa có, chờ exam)">
                                            </div>
                                            
                                            <div id="answerId` + questionCount + `">
                                            <!-- Tùy chọn cho answer -->
                                            </div></div>
                                        
                                        <button type="button" class="btn btn-primary" onclick="addOption(` + questionCount + `)">Add Answer</button>
                                            <button class="btn btn-danger" onclick="deleteQuestion(` + questionCount + `)">Delete Question</button>
                                            <p>Total Weight: <span id="totalWeight`+questionCount+`">0</span></p>
                                            <p class="text-danger" id="weightWarning`+questionCount+`"></p>
                                        </div>
                                        `;
                                        
                                            // Thêm câu hỏi mới vào container
                                            if (questionCount === 1) 
                                            
                                            {
// Nếu đây là câu hỏi đầu tiên, thêm vào đầu container
                    questionContainer.insertBefore(newQuestionItem, questionContainer.firstChild);
                                            } else      {
                    // Nếu không, thêm vào sau câu hỏi trước đó
                    questionContainer.appendChild(newQuestionItem);
                                            }
                                            
                                            
                                            
                                        // Initialize Bootstrap accordion
                                        const newAccordion = new bootstrap.Collapse(newQuestionItem.querySelector('.accordion-button'));
                                            // Add event listener to weight input
                                                const weightInput = newQuestionItem.querySelector(`#weight`+questionCount+``);
                                                    weightInput.addEventListener('input', () => 
                                                    
                                                    
                                                    {
                    updateTotalWeight(questionCount);
                                                    });
                                                    }
                                                    let   answerCount;
                                                    function addOption(questionCount) 
                                                    {
                    const optionContainer = document.getElementById(`answerId` + questionCount + ``);
            answerCount = optionContainer.querySelectorAll('.form-group').length + 1;
            const newOptionInput = document.createElement('div');
            newOptionInput.className = 'form-group';
            newOptionInput.innerHTML = `
<label for="onechoiceOption` + questionCount + `-` + answerCount + `">Answer  :</label>
<input type="text" class="form-control" id="onechoiceOption` + questionCount + `-` + answerCount + `" name="answerName` + questionCount + `-` + answerCount + `" placeholder="Enter answer">
<label for="weightA` + questionCount + `-` + answerCount + `">Trọng số:</label>
<input type="number" class="form-control" value="0" id="weightA` + questionCount + `-` + answerCount + `" name="weightAName` + questionCount + `-` + answerCount + `" placeholder="Nhập trọng số của câu trả lời">
<button class="btn btn-danger" onclick="removeOption(` + questionCount + `, ` + answerCount + `, true)">Remove Option</button>
`;
            // Thêm tùy chọn mới vào containers
            optionContainer.appendChild(newOptionInput);
            // Add event listener to weight input
            const weightInput = newOptionInput.querySelector(`#weightA` + questionCount + `-` + answerCount + ``);
            weightInput.addEventListener('input', () => {
            updateTotalWeight(questionCount);
            });
            }
                
            
            // Hàm để xóa tùy chọn (answer)
            function removeOption(questionCount, answerCount, updateTotal) {
                    const optionContainer = document.getElementById(`answerId` + questionCount + ``);
            const answerToRemove = document.getElementById(`onechoiceOption` + questionCount + `-` + answerCount + ``).parentNode;
            // Xóa câu trả lời
            optionContainer.removeChild(answerToRemove);
            // Lặp qua các câu trả lời còn lại và cập nhật id
            const remainingOptions = optionContainer.querySelectorAll('.form-group');
            remainingOptions.forEach((option, index) => {
            const currentanswerCount = index + 1;
            const label = option.querySelector('label[for^="onechoiceOption"]');
            const input = option.querySelector('input[type="text"]');
            const weightInput = option.querySelector('input[type="number"]');
            const deleteButton = option.querySelector('button.btn-danger');
            // Cập nhật id và label cho câu trả lời
            label.setAttribute('for', `onechoiceOption` + questionCount + `-` + currentanswerCount + ``);
            input.setAttribute('id', `onechoiceOption` + questionCount + `-` + currentanswerCount + ``);
            weightInput.setAttribute('id', `weightA` + questionCount + `-` + currentanswerCount + ``);
            // Cập nhật nút xóa câu trả lời
            deleteButton.setAttribute('onclick', `removeOption(` + questionCount + `, ` + currentanswerCount + `)`);
            });
            // Cập nhật tổng trọng số
            if (updateTotal) {
            updateTotalWeight(questionCount);
            }
                }
                        
                
                // Hàm để cập nhật tổng trọng số
                function updateTotalWeight(questionCount) 
                    
                    {
                    const optionContainer = document.getElementById(`answerId` + questionCount + ``);
            const answerInputs = optionContainer.querySelectorAll('input[type="number"]');
            let totalWeight = 0;
            answerInputs.forEach((input) => {
            const weight = parseInt(input.value);
            totalWeight += Math.max(0, weight); // Ensure that negative weights are treated as 0
            });
            // Update the total weight in the UI
            const totalWeightElement = document.getElementById(`totalWeight` + questionCount + ``);
            totalWeightElement.textContent = totalWeight;
            // Display a warning if the total weight exceeds 100
            const weightWarningElement = document.getElementById(`weightWarning` + questionCount + ``);
            if (totalWeight !== 100) {
            weightWarningElement.textContent = "Warning: Total weight must be 100!";
            weightWarningElement.classList.add("text-danger");
            } else {
            weightWarningElement.textContent = "";
            weightWarningElement.classList.remove("text-danger");
            }
                                    }
                                    
                                    
                                    function checkTotalWeight() {
                    const questionItems = document.querySelectorAll('.question');
            for (let i = 0; i < questionItems.length; i++) {
            const questionCount = i + 1;
            const optionContainer = document.getElementById(`answerId` + questionCount + ``);
            const answerInputs = optionContainer.querySelectorAll('input[type="number"]');
            let totalWeight = 0;
            answerInputs.forEach((input) => {
            const weight = parseInt(input.value);
            totalWeight += Math.max(0, weight); // Ensure that negative weights are treated as 0
            });
            const radioButtonValue = document.querySelector(`input[name="flexRadioDefault` + questionCount + `"]:checked`).value;
            if (radioButtonValue === "onechoice") {
            for (let j = 0; j < answerInputs.length; j++) {
            const answerWeight = parseInt(answerInputs[j].value);
            if (answerWeight !== 0 && answerWeight !== 100) {
            alert("Weight must be either 0 or 100 for Answer " + (j + 1) + " in Question " + questionCount);
            return false; // Prevent form submission
            }
            }
            }
            if (totalWeight !== 100) {
            alert("Total Weight must be 100 for Question " + questionCount); // Hiển thị cảnh báo
            return false; // Ngăn chặn việc submit
            }
            }
            checkTotalQuestionScore();
            return true; // Cho phép submit nếu tổng trọng số của tất cả các câu trả lời đều bằng 100
                    }
                    
                                            
                                            // Hàm để xóa câu hỏi
                                            function deleteQuestion(questionCount) {
                    const questionContainer = document.getElementById("questionContainer");
            const questionToDelete = document.getElementById(`panelsStayOpen-collapse` + questionCount + ``).parentElement;
    
    // Remove the question
    questionContainer.removeChild(questionToDelete);
    
    // Giảm giá trị của QuestionCount
    questionCount--;
    
    // Renumber the remaining questions and update radio buttons
    const questionItems = questionContainer.querySelectorAll('.question');
    
    questionItems.forEach((question, index) => {
        if (index >= questionCount) {
            const currentquestionCount = index + 1; // Tăng lên 1 vì index bắt đầu từ 0
            const header = question.querySelector('.accordion-button');
            header.innerHTML = `Question` + currentquestionCount + ``;
            
            // Cập nhật id cho các phần tử liên quan
            const questionId = header.getAttribute('data-bs-target').substring(22); // Lấy số sau "panelsStayOpen-collapse"
            const newQuestionId = `panelsStayOpen-collapse` + currentquestionCount + ``;
            header.setAttribute('data-bs-target', `#` + newQuestionId + ``);
            question.querySelector('.accordion-collapse').setAttribute('id', newQuestionId);
            question.querySelector('p').setAttribute('id', `question` + currentquestionCount + ``);
            question.querySelector('.btn-danger').setAttribute('onclick', `deleteQuestion(` + currentquestionCount + `)`);
            
            // Cập nhật radio button
            const radioButtons = question.querySelectorAll('input[type="radio"]');
            radioButtons.forEach(radioButton => {
                radioButton.name = `flexRadioDefault` + currentquestionCount + ``;
                radioButton.checked = false; // Hủy chọn tất cả radio buttons
            });
            const oneChoiceRadio = question.querySelector('input[type="radio"][value="onechoice"]');
            oneChoiceRadio.checked = true; // Chọn radio onechoice mặc định
            
            const answerContainer = question.querySelector('[id^="answerId"]');
            answerContainer.setAttribute('id', `answerId` + currentquestionCount + ``);
            
            // Cập nhật ID cho các phần tử trong answerContainer
            const answerInputs = answerContainer.querySelectorAll('input[type="text"]');
            answerInputs.forEach((input, answerCount) => {
                const currentanswerCount = answerCount + 1;
                input.setAttribute('id', `onechoiceOption` + currentquestionCount + `-` + currentanswerCount + ``);
            });
            
            const answerLabels = answerContainer.querySelectorAll('label[for^="onechoiceOption"]');
            answerLabels.forEach((label, answerCount) => {
                const currentanswerCount = answerCount + 1;
                label.setAttribute('for', `onechoiceOption` + currentquestionCount + `-` + currentanswerCount + ``);
            });
            
            const answerWeightInputs = answerContainer.querySelectorAll('input[type="number"]');
            answerWeightInputs.forEach((input, answerCount) => {
                const currentanswerCount = answerCount + 1;
                input.setAttribute('id', `weightA` + currentquestionCount + `-` + currentanswerCount + ``);
            });
            
            const answerDeleteButtons = answerContainer.querySelectorAll('.btn-danger');
            answerDeleteButtons.forEach((button, answerCount) => {
                const currentanswerCount = answerCount + 1;
                button.setAttribute('onclick', `removeOption(` + currentquestionCount + `, ` + currentanswerCount + `)`);
            });
        } 
    }); 
}
function checkTotalQuestionScore() {
    const questionItems = document.querySelectorAll('.question');
    let totalQuestionScore = 0;

    // Calculate the total score of all questions
    for (let i = 0; i < questionItems.length; i++) {
        const questionCount = i + 1;
        const weightInput = document.querySelector(`#weight`+questionCount+``);
        const weight = parseInt(weightInput.value);
        totalQuestionScore += weight;
    }

    const remainingScore = 10 - totalQuestionScore;

    if (remainingScore < 0) {
        alert("Total score exceeds 10. Please adjust the question scores.");
        return false; // Prevent form submission
    } else if (remainingScore > 0) {
        // Distribute the remaining score equally among the questions
        const equalScore = remainingScore / questionItems.length;

        for (let i = 0; i < questionItems.length; i++) {
            const questionCount = i + 1;
            const weightInput = document.querySelector(`#weight`+questionCount+``);
            const weight = parseInt(weightInput.value);
            weightInput.value = weight + equalScore;
        }
    }

    return true; // Allow submission if the total score is within the limit
}

        </script>

    </body>

</html>
