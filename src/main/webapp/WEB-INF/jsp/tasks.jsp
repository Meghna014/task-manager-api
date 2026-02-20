<h2>Cloud Tasks</h2>

<input id="title">
<button onclick="add()">Add Task</button>

<ul id="list"></ul>

<br>
<a href="/logout">Logout</a>

<script>
function load(){
 fetch('/api/tasks')
 .then(r=>r.json())
 .then(d=>{
  list.innerHTML="";
  d.forEach(t=>{
    list.innerHTML+=`<li>${t.title}
     <button onclick="del(${t.id})">X</button></li>`;
  });
 });
}

function add(){
 fetch('/api/tasks',{
   method:'POST',
   headers:{'Content-Type':'application/json'},
   body:JSON.stringify({title:title.value})
 }).then(load);
}

function del(id){
 fetch('/api/tasks/'+id,{method:'DELETE'}).then(load);
}

load();
</script>