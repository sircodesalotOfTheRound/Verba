package com.verba.testtools.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class TaskList implements Task {
  private final List<Task> taskList = new ArrayList<Task>();

  public TaskList() {
  }

  public TaskList(Task... tasks) {
    Collections.addAll(this.taskList, tasks);
  }

  public TaskList(Iterable<Task> tasks) {
    for (Task task : tasks) {
      this.taskList.add(task);
    }
  }

  @Override
  public void perform() {
    for (Task task : taskList) {
      task.perform();
    }
  }

  public void add(Task task) {
    this.taskList.add(task);
  }

  public void add(Task... tasks) {
    Collections.addAll(this.taskList, tasks);
  }
}
