const std = @import("std");

pub fn build(b: *std.Build) void {
    const target = b.standardTargetOptions(.{});
    const optimize = b.standardOptimizeOption(.{});
    const root_module = b.createModule(.{
        .root_source_file = b.path("src/main.zig"),
        .target = target,
        .optimize = optimize,
    });
    const exe = b.addExecutable(.{
        .name = "Zig",
        .root_module = root_module,
    });

    b.installArtifact(exe);

    const parts: [2][2][:0]const u8 = .{
        .{ "part1", "Run the part 1" },
        .{ "part2", "Run the part 2" },
    };

    for (parts) |part| {
        const part_cmd = b.addRunArtifact(exe);
        part_cmd.step.dependOn(b.getInstallStep());
        part_cmd.addArgs(&.{part[0][4..5]});
        const part_step = b.step(part[0], part[1]);
        part_step.dependOn(&part_cmd.step);
    }

    // Tests
    const exe_tests = b.addTest(.{ .root_module = exe.root_module });
    const run_exe_tests = b.addRunArtifact(exe_tests);
    const test_step = b.step("test", "Run tests");
    test_step.dependOn(&run_exe_tests.step);
}
