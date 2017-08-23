package com.redhat.fedora.buildsystem.mbs.pipeline;

import com.google.common.collect.ImmutableSet;
import com.redhat.fedora.buildsystem.mbs.MBSException;
import com.redhat.fedora.buildsystem.mbs.MBSUtils;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
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
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by shebert on 25/07/17.
 */
public class SubmitModuleBuildRequestStep extends Step {

    private String moduleName;
    private String revision;
    private String branch;
    private String mbsUrl;
    private String user;
    private Secret password;

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

    public Secret getPassword() {
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
    public void setPassword(Secret password) {
        this.password = password;
    }

    @DataBoundSetter
    public void setMbsUrl(String mbsUrl) {
        this.mbsUrl = mbsUrl;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new SubmitModuleBuildRequestStep.Execution(this, context);
    }

    public static final class Execution extends AbstractStepExecutionImpl {

        Execution(SubmitModuleBuildRequestStep step, StepContext context) {
            super(context);
            this.step = step;
        }

        @Inject
        private transient SubmitModuleBuildRequestStep step;
        private transient Future task;

        @Override
        public boolean start() throws Exception {

            task = Timer.get().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        SubmittedRequest request = MBSUtils.submitModuleRequest(step.getMbsUrl(),
                                step.getUser(),
                                step.getPassword(),
                                step.getModuleName(),
                                step.getRevision(),
                                step.getBranch(),
                                getContext().get(TaskListener.class));
                        getContext().onSuccess(request);
                    } catch (MBSException mex) {
                        getContext().onFailure(mex);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return false;
        }

        @Override
        public void stop(@Nonnull Throwable throwable) throws Exception {
            task.cancel(true);
        }

        private static final long serialVersionUID = 1L;
    }

    /**
     * Adds the step as a workflow extension.
     */
    @Extension(optional = true)
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
