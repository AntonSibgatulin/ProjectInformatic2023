var decided_info = null;
var decided_info_document = null;
var decided_text = null;
var window_controller = null;

var editor = null;
var task_element = null;

var create_virable = null;
var print_html = null;
var cicle_for_window = null;
var condition = null;
var conditionelif = null;
var split_html = null;
var def_html = null;


var output = null;
var indexCode = 0;
var isDevInited = false;
var codeArray = new Array();

var mainInterval = null;
var array_of_running = [".", "..", "...", ""]

var answer = null;

var count_tasks = null;

function showTheory(a){
    var el = a.parentElement.children[1]
    if(el.style.display=="none"){
       // $(el).show(1500)
        $(el).slideDown(1500)
    }else{
       // $(el).hide(1500)
        $(el).slideUp(1500)
    }
}

async function loadAllConfig(){
    create_virable = await $.get("templates/create-virable.html?id=" + Math.random());
    print_html = await $.get("templates/print.html?id=" + Math.random());
    cicle_for_window = await $.get("templates/cicle-or-window.html?id=" + Math.random());
    condition = await $.get("templates/if.html?id=" + Math.random());
    conditionelif = await $.get("templates/elif.html?id=" + Math.random());
    split_html = await $.get("templates/split.html?id=" + Math.random());
    def_html = await $.get("templates/def.html?id=" + Math.random());
}

function showDecided() {

    if (decided_info_document.style.display == "" || decided_info_document.style.display == "block") {
        decided_info.slideUp(1000);
        decided_text.innerHTML = "Показать решение"
    } else {
        decided_info.slideDown(1000);
        decided_text.innerHTML = "Спрятать решение"

    }

    if (isDevInited == false) {
        decided_info_document.innerHTML = json.solution;

        for (var i = 0; i < indexCode; i++) {

            CodeMirror.fromTextArea(document.getElementById("solution" + i), {
                lineNumbers: true,
                mode: "python",
                matchBrackets: true,
                styleActiveSelected: true,// подсвечивать парные скобки

                value: (codeArray[i])
            });

        }
        isDevInited = true;
        decided_info.append(`<div class="flex-left" style="margin-top: 10px"> <a>Ответ: ` + json.answer + `</a></div>`)
    }

}


function createVirableWindow() {

    window_controller.innerHTML = create_virable
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)

}


function printWindow() {

    window_controller.innerHTML = print_html
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)

}

function cycleForWindow() {

    window_controller.innerHTML = cicle_for_window
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)

    var limit = document.getElementById("limit")
    var in_limits = document.getElementById("in_limits")
    var in_limits_with_way = document.getElementById("in_limits_with_way")
    document.getElementById("type_of_cycle").onchange = function () {

        var value = document.getElementById("type_of_cycle").value;
        if (value == "in_limit" || value == "object") {

            limit.style.display = "block";
            in_limits.style.display = "none";
            in_limits_with_way.style.display = "none";
        }


        if (value == "in_limits") {

            limit.style.display = "none";
            in_limits.style.display = "block";
            in_limits_with_way.style.display = "none";
        }


        if (value == "in_limits_with_way") {

            limit.style.display = "none";
            in_limits.style.display = "none";
            in_limits_with_way.style.display = "block";
        }


    }

}

function conditionWindow() {
    window_controller.innerHTML = condition
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)
}

function conditionelifWindow() {
    window_controller.innerHTML = conditionelif
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)
}


function conditionelifWindow() {
    window_controller.innerHTML = conditionelif
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)
}


function splitWindow() {
    window_controller.innerHTML = split_html
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)
}

function defWindow() {
    window_controller.innerHTML = def_html
    document.body.style.overflow = "hidden"
    $(window_controller.children[0]).slideDown(500)
}


function closeWindow(a) {

    $(a.parentElement.parentElement).slideUp(500);
    setTimeout(function () {
        window_controller.innerHTML = "";
        document.body.style.overflow = "auto"
    }, 500)
    // window_controller.innerHTML = "";
}

function closeWindowParent(a) {

    $(a.parentElement.parentElement.parentElement).slideUp(500);
    setTimeout(function () {
        window_controller.innerHTML = "";
        document.body.style.overflow = "auto"
    }, 500)
    // window_controller.innerHTML = "";
}

function addCode(code) {
    var val1 = editor.getValue()
    var val2 = val1.split("\n")[val1.split("\n").length - 1];
    if (val2.split(" ").length == val2.length + 1) {

    } else {

        var b = isNeedNewEnter()
        if (b == false) {
            newEnter()
        }
    }
    editor.setValue(editor.getValue() + "" + code)
}

function createVirable(a) {


    var name = document.getElementById("name_of_virable").value;
    var type = document.getElementById("type_of_virable").value;
    var value = document.getElementById("value_of_virable").value;
    var code = name + ` = ` + value;
    if (type == "string") {
        code = name + ` = "` + value + `"`;
    }
    addCode(code)
    console.log(code)
    closeWindowParent(a)


}

function printCode(a) {
    var value = document.getElementById("value_of_virable").value;
    var code = `print(` + value + `)`;
    addCode(code)
    closeWindowParent(a)

}

function cycleForCode(a) {


    var value_of_virable_aw = document.getElementById("value_of_virable_aw").value;
    var value_of_virable_bw = document.getElementById("value_of_virable_bw").value;
    var value_of_virable_cw = document.getElementById("value_of_virable_cw").value;


    var name_of_virable = document.getElementById("name_of_virable").value;
    var value_of_virable = document.getElementById("value_of_virable").value;
    var value_of_virable_a = document.getElementById("value_of_virable_a").value;
    var value_of_virable_b = document.getElementById("value_of_virable_b").value;

    var code = null;


    var value = document.getElementById("type_of_cycle").value;


    if (value == "in_limit" || value == "object" || value == "object_of_num") {

        if (isNaN(Number(value_of_virable)) == false) {
            code = `for ` + name_of_virable + ` in range(` + value_of_virable + `):`
        } else {
            code = `for ` + name_of_virable + ` in ` + value_of_virable + `:`
        }

        if (value == "object_of_num") {
            code = `for ` + name_of_virable + ` in range(` + value_of_virable + `):`

        }

    }


    if (value == "in_limits") {
        code = `for ` + name_of_virable + ` in range(` + value_of_virable_a + `,` + value_of_virable_b + `):`;


    }


    if (value == "in_limits_with_way") {
        code = `for ` + name_of_virable + ` in range(` + value_of_virable_aw + `,` + value_of_virable_bw + `,` + value_of_virable_cw + `):`;


    }

    addCode(code)
    closeWindowParent(a)

}


function createCondition(a) {
    var name_of_virable = document.getElementById("name_of_virable").value;
    var code = `if ` + name_of_virable + `:`
    if (name_of_virable.endsWith("::")) {
        code = `if ` + name_of_virable
    }

    addCode(code)
    closeWindowParent(a)

}


function createConditionelif(a) {
    var name_of_virable = document.getElementById("name_of_virable").value;
    var code = `elif ` + name_of_virable + `:`
    if (name_of_virable.endsWith("::")) {
        code = `elif ` + name_of_virable
    }

    addCode(code)
    closeWindowParent(a)

}


function createElse() {

    var code = `else:`
    addCode(code)


}

function createSplit(a) {

    var name_of_virable = document.getElementById("name_of_virable").value;
    var value_of_virable = document.getElementById("value_of_virable").value;
    var value_of_virable_a = document.getElementById("value_of_virable_a").value;
    var value_of_virable_b = document.getElementById("value_of_virable_b").value;

    var code = name_of_virable + ` = ` + value_of_virable + `.split("` + value_of_virable_a + `","` + value_of_virable_b + `")`;
    addCode(code)
    closeWindowParent(a)

}

function createDef(a) {
    var name = document.getElementById("name_of_virable").value;
    var value = document.getElementById("value_of_virable").value;
    var code = `def ` + name + `(` + value + `):`;

    addCode(code)

    closeWindowParent(a)

}

function addJoin() {
    addCode(`.join()`)

}

function addRight() {
    addCode(`>`)

}

function addLeft() {
    addCode(`>`)

}

function addCenter() {
    addCode(`=`)

}


function addOr() {
    addCode(`or`)

}


function addAnd() {
    addCode(`and`)

}


function generateSpace(a) {
    var b = "";
    for (var i = 0; i < a; i++) {
        b = b + " ";
    }
    return b;
}

function countOfSpace() {
    var val1 = editor.getValue()
    var val2 = val1.split("\n")[val1.split("\n").length - 1];
    for (var i = 1; i < val2.length; i++) {
        var str = val2.substr(0, i);
        if (str.split(" ").length <= str.length) {
            return i - 1
        }
    }
}


function isNeedNewEnter() {
    var val1 = editor.getValue()
    var val2 = val1.split("\n")[val1.split("\n").length - 1];
    if (val2.includes("for") || val2.includes("if") || val2.includes("elif") || val2.includes("def")) {
        newEnterWithTub()
        return true;
    }
    return false
}

function newEnter() {
    var c = countOfSpace();
    var str = generateSpace(c);
    editor.setValue(editor.getValue() + "\n" + str)
}


function newEnterWithTub() {
    var c = countOfSpace();
    var str = generateSpace(c + 4);
    editor.setValue(editor.getValue() + "\n" + str)
}


function showDecidedById(id) {
    var doc = document.getElementById("decided_info_" + id);
    var text = document.getElementById("decided_text_" + id);
    if (doc.style.display == "none" || doc.style.display == "none;") {
        text.innerHTML = "Спрятать решение"
        $(doc).slideDown(500);

    } else {
        text.innerHTML = "Показать решение"
        $(doc).slideUp(500);

    }
    setTimeout(function () {
        var el = document.getElementById("code_d_" + id);
        if (el.style.display != "none" && el.style.display != "none;") {
            CodeMirror.fromTextArea(el, {
                lineNumbers: true,
                mode: "python",
                matchBrackets: true,
                styleActiveSelected: true
            });
        }
    }, 200)

}

function hideElement(a) {
    $(a).hide(400)
    $(a.parentElement.parentElement).slideUp(2000)
}
function hideWithDeleteElement(a) {
    $(a).hide(400)
    $(a.parentElement.parentElement).slideUp(2000)
    setTimeout(function(){
        a.parentElement.parentElement.remove();
    },2000)

}



function strJS() {
    var result = ""

    for (var i = 0; i < arguments.length; i++) {
        if (i == 0) {
            result += arguments[i];
        } else {
            result += " " + arguments[i];
        }
    }

    return result + "\n";
}

async function runCode() {
    runIntervalRunning();

    setTimeout(async function () {

        output.value = "";
        let pyodide = await loadPyodide();
        // Pyodide is now ready to use...
        try {
            var code = `import js\nfrom js import strJS\nfrom js import end_runnable_task\n` + ((editor.getValue()+`\nend_runnable_task()`).replaceAll("print", `js.document.getElementById(\"output_data_textarea\").value+=strJS`));

            await(pyodide.runPython(code));
        } catch (err) {
            output.value += (err);
        }

    }, 1200);


};

function runIntervalRunning() {
    var i = 0;
    mainInterval = setInterval(function () {

        var doc = document.getElementById("status_of_code")
        doc.innerHTML = "Running " + array_of_running[i];
        i++;
        if (i >= array_of_running.length) {
            i = 0;
        }

    }, 500);

}

function end_runnable_task() {
    clearInterval(mainInterval)
    var doc = document.getElementById("status_of_code")
    doc.innerHTML = "Ready";
    if(answer!=null) {
        if (answer == output.value) {
            alert("Ответ верный.Отлично!")
        }else{
            alert("Ответ не верный.Попробуй ещё раз!")
        }


    }

}
var isMobileDetectWithOnTouchStart = 'ontouchstart' in window;


const isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};


function openmenu(){
    var mainController = document.getElementById("mainController")
    var panel = document.getElementById("leftPanel")
    var container = document.getElementById("container")
    if(panel.style.display=="") {
        if (innerWidth < 500) {
            container.style = "display:none;";
            panel.style = "margin: auto;display:block;width:100%";
        }

        if (innerWidth < 700 && innerWidth>=500) {
            container.style = "width:500px";
            panel.style = "    margin-left: -30px;display:block";
        }
        if(innerWidth>700){

            panel.style = " margin-left:auto;display:block";
            mainController.style="margin-left: auto;";
        }
    }else{
       // if (innerWidth < 500) {
            container.style = "";
            panel.style = "";
            mainController.style="";
       // }
    }
}
