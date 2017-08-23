import hudson.util.Secret

node() {
    submission = submitModuleBuildRequest mbsUrl: "http://localhost:32000",
            user: "scott",
            password: Secret.fromString("scott"),
            module: "mymodule",
            rev: "myrev",
            branch: "mybranch"
    echo "my submission id is: " + submission.getId()
    sleep 10
}