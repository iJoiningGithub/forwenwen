package com.forwenwen.happy.repository;

import com.forwenwen.happy.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<File, String>, JpaSpecificationExecutor<File> {

}
