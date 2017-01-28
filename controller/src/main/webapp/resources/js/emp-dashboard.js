$('.status-button').on('click', function () {
    var button = $(this);
    var taskId = $(this).data('id');
    var task = {
        "id": taskId
    };
    $.ajax({
        url: '/update-task-status',
        data:JSON.stringify(task),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            var tr = $(button).closest("tr").detach();
            switch (data.message){
                case 'STARTED':
                    var targetTable = $('#table-started');
                    $('#tasksNewCount').text(parseInt($('#tasksNewCount').text())-1);
                    $('#tasksInWorksCount').text(parseInt($('#tasksInWorksCount').text())+1)
                    var newButton = $(tr).children().last().children();
                    $(newButton).removeClass('btn-success');
                    $(newButton).addClass('btn-danger');
                    $(newButton).children().remove();
                    $(newButton).append($('<i class="fa fa-times" aria-hidden="true"></i>'));
                    break;
                case 'FINISHED':
                    var targetTable = $("#table-finished");
                    $('#tasksInWorksCount').text(parseInt($('#tasksInWorksCount').text())-1);
                    $('#tasksFinishedCount').text(parseInt($('#tasksFinishedCount').text())+1);
                    $(tr).children().last().remove();
                    break;
            }
            targetTable.append(tr);
        },
        error: function (data) {
          alert('Dependent task must be finished');
        }
    });
});