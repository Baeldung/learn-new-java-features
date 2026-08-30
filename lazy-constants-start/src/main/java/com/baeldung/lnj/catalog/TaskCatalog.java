package com.baeldung.lnj.catalog;

import java.util.List;

import com.baeldung.lnj.domain.model.Task;

record TaskCatalog(List<Task> tasks) {
}
