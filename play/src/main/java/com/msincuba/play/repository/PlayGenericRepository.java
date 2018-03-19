package com.msincuba.play.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PlayGenericRepository <T, Id extends Serializable, Qt extends EntityPath<?>>
    extends JpaRepository<T, Id>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<Qt>{
    
    @Override
    default public void customize(QuerydslBindings bindings, Qt root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));        
    }
}
