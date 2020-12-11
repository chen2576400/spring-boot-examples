package ext.st.pmgt.indicator.processors;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteOTIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        return new ResponseWrapper<>(ResponseWrapper.FAILED, "此指标本项目不使用\n" +
                "通过如下操作，实现业务意义上的裁剪\n" +
                "由此指标的OT责任人，上传项目经理批准的文件或者自撰的说明文件，文件中描述本项目或者本次无需此文档", null);
    }
}
