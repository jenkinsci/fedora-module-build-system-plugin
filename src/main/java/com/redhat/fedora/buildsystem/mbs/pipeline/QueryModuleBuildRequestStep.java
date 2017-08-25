package com.redhat.fedora.buildsystem.mbs.pipeline;

import com.google.common.collect.ImmutableSet;
import com.redhat.fedora.buildsystem.mbs.MBSException;
import com.redhat.fedora.buildsystem.mbs.MBSUtils;
import com.redhat.fedora.buildsystem.mbs.model.QueryResult;
import com.redhat.fedora.buildsystem.mbs.model.SubmittedRequest;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.util.Secret;
import jenkins.util.Timer;
import org.jenkinsci.plugins.workflow.steps.AbstractStepExecutionImpl;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by shebert on 25/07/17.
 */
public class QueryModuleBuildRequestStep extends Step {

    private String mbsUrl;

    public String getMbsUrl() {
        return mbsUrl;
    }

    @DataBoundConstructor
    public QueryModuleBuildRequestStep(String mbsUrl) {
        this.mbsUrl = mbsUrl;
    }

    @DataBoundSetter
    public void setMbsUrl(String mbsUrl) {
        this.mbsUrl = mbsUrl;
    }
    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new QueryModuleBuildRequestStep.Execution(this, context);
    }

    public static final class Execution extends AbstractStepExecutionImpl {

        Execution(QueryModuleBuildRequestStep step, StepContext context) {
            super(context);
            this.step = step;
        }

        @Inject
        private transient QueryModuleBuildRequestStep step;
        private transient Future task;

        @Override
        public boolean start() throws Exception {

            task = Timer.get().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        QueryResult result = MBSUtils.query(step.getMbsUrl() + MBSUtils.MBS_URLPREFIX,
                                getContext().get(TaskListener.class));
                        getContext().onSuccess(result);
                    } catch (MBSException mex) {
                        getContext().onFailure(mex);
                        mex.printStackTrace();
                    } catch (InterruptedException e) {
                        getContext().onFailure(e);
                        e.printStackTrace();
                    } catch (IOException e) {
                        getContext().onFailure(e);
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
            return "queryModuleBuildRequest";
        }

        @Override
        public String getDisplayName() {
            return "Query Module Build Request";
        }

    }


}
