async function deleteTask(a){

   var id =  Number(a.parentElement.parentElement.id.split("_")[1]);
   var deleted = await $.get("/admin/delete?type=task&id="+id);

    if(deleted=="ok"){
        hideElement(a)
    }else{
        alert(deleted)
    }


}


async function createTask(){

    var text = document.getElementById("task_text").value;
    var solution = document.getElementById("solution_text").value.replaceAll("\n",'<div class="new-wrap"></div>')
    solution = solution+"<code>"+editor.getValue()+"</code>";
    var lang = "PYTHON";
    var answer = document.getElementById("output_data_textarea").value;
    var type = document.getElementById("type_select").value
    var author = document.getElementById("author").value

    var getrequest = await $.post("/admin/createtask",{text:text,solution:solution,lang:lang,
        answer:answer,type:type,author:author
    });
    var str_split = getrequest.split(";")
    if(str_split[0]=="ok"){
        window.open(window.location.origin+"/task?id="+str_split[1],"_blank");
        window.location.reload();
    }else{
        alert(getrequest)
    }


}


async function editTask(){
    var text = document.getElementById("task_text").value;
    var solution = document.getElementById("solution_text").value.replaceAll("\n",'<div class="new-wrap"></div>')
    solution = solution+"<code>"+editor.getValue()+"</code>";
    var lang = "PYTHON";
    var answer = document.getElementById("output_data_textarea").value;
    var type = document.getElementById("type_select").value
    var author = document.getElementById("author").value

    var getrequest = await $.post("/admin/edittask",{text:text,solution:solution,lang:lang,
        answer:answer,type:type,author:author,id:id
    });
    var str_split = getrequest.split(";")
    if(str_split[0]=="ok"){
        window.location.href=window.location.origin+"/task?id="+str_split[1]
        
    }else{
        alert(getrequest)
    }
}
function runEditTask(){
    var getId = prompt("Введите id задания");
    if(isNaN(Number(getId))==true || getId=="" || getId.length<=0){
        alert("Введенные данные должны быть числом")
        return;
    }
    var id = Number(getId);
    window.location.href = window.location.origin+"/task?id="+id;

}

function openEditTask(a){
    var str_split = a.parentElement.parentElement.id.split("_");
    window.open(window.location.origin+"/admin/edit?id="+str_split[1],"_blank");

}
function openEditTaskById(a){

    window.open(window.location.origin+"/admin/edit?id="+a,"_blank");

}



async function createTypeOfTask(){
    var number = prompt("Введите тип задания(число).")
    var hum = Number(number);
    if(isNaN(number)==true){
        alert("Надо ввести число!")
        return;
    }
    var text = prompt("Введте описание.")
    var html = await $.post("/admin/edittype",{
        type:number,
        text:text
    })
    if(html=="ok"){
        window.location.reload();
    }else{
        alert(html);
    }

}



async function editTypeOfTask(id){
    var number = prompt("Введите тип задания(число).")
    var hum = Number(number);
    if(isNaN(number)==true){
        alert("Надо ввести число!")
        return;
    }
    var text = prompt("Введте описание.")
    var html = await $.post("/admin/edittype",{
        id:id,
        type:number,
        text:text
    })
    if(html=="ok"){
        window.location.reload();
    }else{
        alert(html);
    }

}


async function deleteTypeOfTask(id){

     var html = await $.post("/admin/removetype",{
        id:id
    })
    if(html=="ok"){
        window.location.reload();
    }else{
        alert(html);
    }

}



function editTheory(a){

    var id = a.parentElement.parentElement.id.split("_")[1];
    window.location.href="/admin/edittheory?id="+id;

}


async function deleteTheory(a){
    var id = a.parentElement.parentElement.id.split("_")[1];
    var html = await $.post("/admin/deletetheory",{id:id});
    if(html=="ok"){
        window.location.href="/admin/theory"
    }else{
        alert(html)
    }
}
async function updateTheory(a){
    var id = a.id.split("_")[1];
    var name = document.getElementById("header").value;
    var description = document.getElementById("description").value;
    var html = await $.post("/admin/updatetheory",{id:id,name:name,description:description});
    if(html=="ok"){
        window.location.href="/admin/theory"
    }else{
        alert(html)
    }
}

async function saveTheory(){

    var name = document.getElementById("header").value;
    var description = document.getElementById("description").value;
    var html = await $.post("/admin/updatetheory",{name:name,description:description});
    if(html=="ok"){
        window.location.href="/admin/theory"
    }else{
        alert(html)
    }
}



//////////////////////////////////////////


function editDirectory(a){

    var id = a.parentElement.parentElement.id.split("_")[1];
    window.location.href="/admin/editdirectory?id="+id;

}


async function deleteDirectory(a){
    var id = a.parentElement.parentElement.id.split("_")[1];
    var html = await $.post("/admin/deletedirectory",{id:id});
    if(html=="ok"){
        window.location.href="/admin/directory"
    }else{
        alert(html)
    }
}
async function updateDirectory(a){
    var id = a.id.split("_")[1];
    var name = document.getElementById("header").value;
    var description = document.getElementById("description").value;
    var html = await $.post("/admin/updatedirectory",{id:id,name:name,description:description});
    if(html=="ok"){
        window.location.href="/admin/directory"
    }else{
        alert(html)
    }
}

async function saveDirectory(){

    var name = document.getElementById("header").value;
    var description = document.getElementById("description").value;
    var html = await $.post("/admin/updatedirectory",{name:name,description:description});
    if(html=="ok"){
        window.location.href="/admin/directory"
    }else{
        alert(html)
    }
}