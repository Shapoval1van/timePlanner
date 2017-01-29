$('.priority-selector').on('change',function(){
    var val1 = $(this).find(':selected').attr('label');
    var data = $(this).find(':selected').data("value");
    console.log(data);
    $.ajax({
        url: '/set-priority',
        data:JSON.stringify(data),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    switch (val1){
        case 'LOW':
            $(this).removeClass('btn-info btn-warning btn-danger');
            break;
        case 'MEDIUM':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-info');
            break;
        case 'HIGH':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-warning');
            break;
        case 'CRITICAL':
            $(this).removeClass('btn-info btn-warning btn-danger').addClass('btn-danger');
            break;
        default:
            break;
    }
});


var mass = Array.from($(".priority option:selected"));
mass.forEach(function(item, i, mass){
    var val  = $(item).attr('label');
    switch (val){
        case 'LOW':
            break;
        case 'MEDIUM':
            $(item).parent().addClass('btn-info');
            break;
        case 'HIGH':
            $(item).parent().addClass('btn-warning');
            break;
        case 'CRITICAL':
            $(item).parent().addClass('btn-danger');
            break;
        default:
            break;
    }
});

$('#status-btn').click(function () {
    var projectId = $('#status-btn').data("id");
    var isStarted = $('#status-btn').data("started");
    var isFinished = $('#status-btn').data("finished");
    var project = {"id":projectId, "name":null, "description":null, "isStarted":isStarted,
        "isFinished":isFinished, "startDate":null, "finishDate":null, "planFinishDate":null,
        "customers":null,"company":null,"sprints":null};
    $.ajax({
        url: '/update-project-status',
        data:JSON.stringify(project),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });

    if($('#status-btn').hasClass('btn-success')){
        $('#status-btn').removeClass('btn-success');
        $('#status-btn').addClass('btn-danger');
        $('#status-btn').data('started',true);
        $('#status-btn').text('Finish project')
    }else if($('#status-btn').hasClass('btn-danger')){
        $('#status-btn').removeClass('btn-danger');
        $('#status-btn').addClass('btn-info');
        $('#status-btn').attr('disabled', 'disabled');
        $('#status-btn').text('Finished')
    }
});

$('.project-select').on('change',function(){
    var projectId1 = $(this).find(':selected').val();
    window.location.href = '/dashboard-pm?id='+projectId1;
});

$('.sprint-status-btn').click(function () {
    var sprintId = $(this).data("id");
    var sprintIsStarted = $(this).data("started");
    var sprintIsFinished = $(this).data("finished");
    var sprintStatusButton = $(this);
    var sprint = {
        "id": sprintId,
        "isStarted": sprintIsStarted,
        "isFinished": sprintIsFinished
    };
    $.ajax({
        url: '/update-sprint-status',
        data:JSON.stringify(sprint),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            switch(data.message){
                case 'STARTED':
                    $(sprintStatusButton).removeClass('btn-success');
                    $(sprintStatusButton).addClass('btn-danger');
                    $(sprintStatusButton).text('Finish');
                    $(sprintStatusButton).data("started",true);
                    break;
                case 'FINISHED':
                    $(sprintStatusButton).removeClass('btn-danger');
                    $(sprintStatusButton).addClass('btn-info');
                    $(sprintStatusButton).text('Finished');
                    $(sprintStatusButton).attr('disabled', 'disabled');
                    $(sprintStatusButton).data("finished",true);
                    break;
                default:
                    alert('You cant finish sprint. At first finish employees must dependent finish task')
                    break;
            }
        },
        error: function (data) {
            alert('You cant finish sprint. At first finish employees must dependent finish task');
        }
    })
});