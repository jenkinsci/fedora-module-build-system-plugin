import hudson.util.Secret

node() {
    submission = submitModuleBuildRequest mbsUrl: "http://localhost:32000",
            user: "scott",
            password: Secret.fromString("scott"),
            module: "mymodule",
            rev: "myrev",
            branch: "mybranch"
    echo "my submission id is: " + submission.getId()
    waitUntil {
        query = queryModuleBuildRequest mbsUrl: "http://localhost:32000"
        echo "here"
        return query.isModuleReady(1)
    }
    echo "Module id: 1 is ready"

}