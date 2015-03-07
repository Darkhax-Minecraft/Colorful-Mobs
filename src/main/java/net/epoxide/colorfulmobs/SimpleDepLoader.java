package net.epoxide.colorfulmobs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

@IFMLLoadingPlugin.Name(value = "SimpleDepLoader")
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
public class SimpleDepLoader implements IFMLLoadingPlugin, IFMLCallHook {
    ArrayList<String> modsList = new ArrayList<String>();
    private static ArrayList<String> moduleContainers = new ArrayList<String>();

    public void registerDownloadMods() {
        modsList.add("");
    }

    @Override
    public String[] getASMTransformerClass() {

        return new String[] {};
    }

    @Override
    public String getModContainerClass() {

        return null;
    }

    @Override
    public String getSetupClass() {

        return getClass().getName();
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {

        return null;
    }


    @Override
    public Void call() throws Exception {

        registerDownloadMods();

        // check for dependencies
        System.out.println("Downloading: " + modsList);
        for (String url : modsList)
            download(url);

        // check to see if already exist, if not copy
        File target = new File(FMLInjectionData.data()[6] + "/mods");
        for (File fileEntry : target.listFiles(new ClassFilter())) {

            loadModuleFiles(fileEntry);
        }

        target = new File(FMLInjectionData.data()[6] + "/mods/temp");
        for (File fileEntry : target.listFiles(new ClassFilter())) {
            compare(fileEntry);
        }
        return null;
    }

    public void loadModuleFiles(File mod) {
        String modid = getModid(mod);
        if (modid != null)
            moduleContainers.add(modid);
    }

    private void compare(File mod) {
        try {
            if (!moduleContainers.contains(getModid(mod))) {
                System.out.println(mod.getName());
                FileUtils.copyFileToDirectory(mod, mod.getParentFile().getParentFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getModid(File mod) {
        try {
            // Creates readable stream of data from the module jar.
            InputStream inputStream = new FileInputStream(mod);
            JarInputStream jar = new JarInputStream(inputStream);

            ZipEntry file = null;

            // loops through files within the module jar
            while ((file = jar.getNextEntry()) != null) {

                String entryName = file.getName();

                // checks if the current file is a class file.
                if (entryName.endsWith(".class")) {
                    String modid;
                    if ((modid = isModuleMainClass(jar)) != null) {
                        return modid;
                    }
                }
            }
            jar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String isModuleMainClass(InputStream stream) {
        try {
            // creates a new classnode instance.
            ClassNode classNode = new ClassNode();
            new ClassReader(stream).accept(classNode, 0);

            // Creates list of visible annotations within the class.
            List annotations = classNode.visibleAnnotations;

            // Checks for at least one valid annotation
            if (annotations != null && !annotations.isEmpty()) {

                // loops through all annotations.
                for (int i = 0; i < annotations.size(); i++) {

                    // Checks if current annotation is the @Module annotation.
                    if (Type.getType(((AnnotationNode) annotations.get(i)).desc).equals(Type.getType(Mod.class))) {

                        // Creates a temporary list of this annotations values.
                        List temp = ((AnnotationNode) annotations.get(i)).values;

                        if (temp.contains("modid")) {
                            String modid = "modid";
                            for (int j = 0; j < temp.size(); j++) {
                                if (temp.get(j).equals("modid")) {
                                    modid = (String) temp.get(j + 1);
                                    break;
                                }
                            }
                            return modid;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ClassFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String fileName) {
            return fileName.toLowerCase().endsWith(".jar") || fileName.toLowerCase().endsWith(".zip");
        }

    }

    private void download(String dependent) {

        try {
            URL url = new URL(dependent);

            File target = new File(FMLInjectionData.data()[6] + "/mods/temp/", getFileName(url));

            if (!target.exists()) {
                target.getParentFile().mkdirs();
                target.createNewFile();

                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(target);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(URL extUrl) {

        String filename = "";
        String path = extUrl.getPath();
        String[] pathContents = path.split("[\\\\/]");
        int pathContentsLength = pathContents.length;
        String lastPart = pathContents[pathContentsLength - 1];
        String[] lastPartContents = lastPart.split("\\.");
        if (lastPartContents.length > 1) {
            int lastPartContentLength = lastPartContents.length;
            String name = "";
            for (int i = 0; i < lastPartContentLength; i++) {
                if (i < (lastPartContents.length - 1)) {
                    name += lastPartContents[i];
                    if (i < (lastPartContentLength - 2)) {
                        name += ".";
                    }
                }
            }
            String extension = lastPartContents[lastPartContentLength - 1];
            filename = name + "." + extension;
        }

        return filename;
    }
}