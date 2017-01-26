$(document).ready(function() {
    $(".employee-selector").select2();
});


$('.btn').on('click', function () {
    var projectId = $(this).data('project');
    var id = $(this).data('id');
    var selectorId = '#employee-selector-id'+id;
    var selector =  $(selectorId);
    var selectedOption = $(selector).find(':selected');
    if(selectedOption.length!=0){
        var task = {
            "id": id,
            "users": []
        };
        for(var i=0; i < selectedOption.length; i++){
            task.users.push({"id":selectedOption[i].value});
        }
        $.ajax({
            url: '/assign-tasks',
            data:JSON.stringify(task),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function () {
                console.log(projectId);
                window.location.href = '/assign-tasks/for-'+projectId+'id';
            }
        });
    }
});
