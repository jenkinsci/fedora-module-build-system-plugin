package com.redhat.fedora.buildsystem.mbs.pipeline;

import com.google.common.collect.ImmutableSet;
import com.redhat.fedora.buildsystem.mbs.MBSException;
import com.redhat.fedora.buildsystem.mbs.MBSUtils;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
import hudson.AbortException;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import jenkins.util.Timer;
import org.jenkinsci.plugins.workflow.steps.AbstractStepExecutionImpl;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Future;

/*
 * The MIT License
 *
 * Copyright (c) Red Hat, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class SubmitModuleBuildRequestStep extends Step {

    private String mbsUrl;
    private String user;
    private String password;
    private String moduleName;
    private String revision;
    private String branch;

    public String getModuleName() {
        return moduleName;
    }

    public String getRevision() {
        return revision;
    }

    public String getBranch() {
        return branch;
    }

    public String getMbsUrl() {
        return mbsUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @DataBoundConstructor
    public SubmitModuleBuildRequestStep(String moduleName, String revision, String branch) {
        this.moduleName = moduleName;
        this.revision = revision;
        this.branch = branch;
    }

    @DataBoundSetter
    public void setUser(String user) {
        this.user = user;
    }

    @DataBoundSetter
    public void setPassword(String password) {
        this.password = password;
    }

    @DataBoundSetter
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @DataBoundSetter
    public void setRevision(String revision) {
        this.revision = revision;
    }

    @DataBoundSetter
    public void setBranch(String branch) {
        this.branch = branch;
    }

    @DataBoundSetter
    public void setMbsUrl(String mbsUrl) {
        this.mbsUrl = mbsUrl;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new SubmitModuleBuildRequestStep.Execution(this, context);
    }

    public static final class Execution extends SynchronousNonBlockingStepExecution<SubmittedRequest> {

        Execution(SubmitModuleBuildRequestStep step, StepContext context) {
            super(context);
            this.step = step;
        }

        @Inject
        private transient SubmitModuleBuildRequestStep step;

        @Override
        public SubmittedRequest run() throws Exception {
            try {
                SubmittedRequest request = MBSUtils.submitModuleRequest(step.getMbsUrl() + MBSUtils.MBS_URLPREFIX,
                        step.getUser(),
                        step.getPassword(),
                        step.getModuleName(),
                        step.getRevision(),
                        step.getBranch(),
                        getContext().get(TaskListener.class));
                return request;
            } catch (MBSException mex) {
                throw new AbortException(mex.getMessage());
            }
        }

        private static final long serialVersionUID = 1L;
    }

    /**
     * Adds the step as a workflow extension.
     */
    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override public Set<? extends Class<?>> getRequiredContext() {
            return ImmutableSet.of(Run.class, Launcher.class, TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "submitModuleBuildRequest";
        }

        @Override
        public String getDisplayName() {
            return "Submit Module Build Request";
        }

    }


}
