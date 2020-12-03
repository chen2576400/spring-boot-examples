package ext.st.pmgt.indicator.test;

import com.pisx.tundra.pmgt.plan.model.PIPlan;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

@Data
public class Result {

    public Result() {

    }

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result ok(String message) {
        return new Result(0, message);
    }

    public static Result error(String message) {
        return new Result(1, message);
    }

    public static Result error(Integer status, String message) {
        return new Result(status, message);
    }

    private Integer status;
    private String message;


}
