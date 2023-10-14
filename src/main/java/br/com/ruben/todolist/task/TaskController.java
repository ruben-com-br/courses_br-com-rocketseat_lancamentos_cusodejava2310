package br.com.ruben.todolist.task;

import br.com.ruben.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.ruben.todolist.utils.DateUtils.validarDatas;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Chegou no controller "+ request.getAttribute("idUser"));

        salvarIdUser(taskModel,request);

        var task = this.taskRepository.save(taskModel);

        var result = validarDatas(taskModel.getStartAt(), taskModel.getEndAt());
        if ( result != null ) { return result;};

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
        //salvarIdUser(taskModel,request);


        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não Encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permsissão para alterar uma tarefa");
        }
        Utils.copyNonNullProperties(taskModel,task);

        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);

    }

    private void salvarIdUser(TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
    }

}
