package demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample REST controller just for demonstration purposes. NOTE: this code is not thread
 * safe so will not work if multiple clients are connected. In a real example this might
 * be an edge service (e.g. using zuul).
 */
@RestController
@RequestMapping("/api/todos")
public class ToDoController {

	private List<ToDo> todos = new ArrayList<>();

	private int id;

	@RequestMapping(method = RequestMethod.GET)
	public List<ToDo> get() {
		return this.todos;
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<ToDo> post(@RequestBody ToDo todo) {
		this.todos.add(todo.withId(this.id++));
		return this.todos;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public List<ToDo> delete(@PathVariable int id) {
		this.todos.removeIf(todo -> todo.getId() == id);
		return this.todos;
	}

}
