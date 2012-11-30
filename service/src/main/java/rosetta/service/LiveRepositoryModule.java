package rosetta.service;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.codehaus.jackson.map.ObjectMapper;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class LiveRepositoryModule extends AbstractModule {
    @Override protected void configure() {
    }

    @Provides
    public Projects projects(ObjectMapper objectMapper){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from rosetta.projects_proxy import ProjectsProxy");
        PyObject projectsProxyClass = interpreter.get("ProjectsProxy");
        PyObject pyObject = projectsProxyClass.__call__(Py.java2py(objectMapper));
        return (Projects) pyObject.__tojava__(Projects.class);
    }
}
