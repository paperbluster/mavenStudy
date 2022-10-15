package com.example.spring.base;

import java.io.Serializable;

/**实体对象基类
 * @author wanjun
 * @create 2022-10-04 14:24
 */
public abstract class BaseModel<PK extends Comparable<PK> & Serializable> implements IEntity<PK>,Serializable {
    private static final long serialVersionUID=-8011061374263995942L;

    public abstract PK getId();

    public abstract PK setId(PK id);

    public boolean equals(Object o){
        if(o==this){
            return true;
        }
        if(!(o instanceof BaseModel)){
            return false;
        }
        if(o.getClass()!=getClass()){
            return false;
        }
        BaseModel rhs=(BaseModel) o;
        return this.getId()!=null&&rhs.getId()!=null&&this.getId().equals(rhs.getId());
    }

    public PK getIdentity(){
        return getId();
    }


}
