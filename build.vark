ProjectName = "EnhancedJavaBeanNavigator"
DefaultTarget = "run"
BaseDir = file(".")

var srcDir = file("src")
var testDir = file("test")
var libDir = file("lib")
var classesDir = file("build/classes")
var testClassesDir = file("build/tests")
var distDir = file("build/dist")

function deps() {
  Ivy.retrieve(:sync = true, :log = "download-only")
}

@Depends("deps")
function compile() {
  Ant.mkdir(:dir = classesDir)
  Ant.javac(:srcdir = path(srcDir),
            :debug = true,
            :classpath = classpath()
              .withFileset(libDir.fileset()),
            :destdir = classesDir,
            :includeantruntime = false)
}

@Depends("compile")
function run() {
  Ant.java(:classname = "com.jpcamara.javabean.Main",
           :classpath = path(libDir.fileset()).withFile(classesDir),
           :fork = true)
/*           :arg = "-Xlint:unchecked")*/
}

function clean() {
  Ant.delete(:dir = classesDir)
  Ant.delete(:dir = distDir)
}

/*@Depends("compile")
function compileTests() {
  Ant.mkdir(:dir = testClassesDir)
  Ant.javac(:srcdir = path(testDir),
            :debug = true,
            :classpath = classpath().withFile(classesDir)
              .withFileset(gosuDir.fileset())
              .withFileset(libDir.fileset()),
            :destdir = testClassesDir,
            :includeantruntime = false)
}*/

/*@Depends("compileTests")
function test() {

  var formatterElement = new org.apache.tools.ant.taskdefs.optional.junit.FormatterElement()
  var attr = org.apache.tools.ant.types.EnumeratedAttribute.getInstance(org.apache.tools.ant.taskdefs.optional.junit.FormatterElement.TypeAttribute, "plain")
  formatterElement.setType(attr as org.apache.tools.ant.taskdefs.optional.junit.FormatterElement.TypeAttribute)
  
  Ant.junit(:fork = true, :printsummary = Yes, :haltonfailure = true, :haltonerror = true,
    :classpathBlocks = {
      \ p -> p.withFileset(libDir.fileset("*.jar", null)),
      \ p -> p.withFileset(gosuDir.fileset("*.jar", null)),
      \ p -> p.withFile(classesDir),
      \ p -> p.withFile(testClassesDir),
      \ p -> p.withFile(testDir)
    },
    :formatterList = {
      formatterElement
    },
    :testList = {
      new org.apache.tools.ant.taskdefs.optional.junit.JUnitTest("org.jschema.test.GosonSuite")
    })
}*/
